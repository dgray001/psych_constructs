package fyi.lnz.psych_constructs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.protobuf.Message;
import com.google.protobuf.Descriptors.FieldDescriptor;

import fyi.lnz.psych_constructs.database.migrations.*;
import fyi.lnz.psych_constructs.util.ArrU;
import fyi.lnz.psych_constructs.util.Constants;
import fyi.lnz.psych_constructs.util.Str;

record QueryResult(boolean success, ResultSet result, Integer rows, String error) {
}

@Service
public class DatabaseConnection {
  private Connection connection;
  private static String[] table_names = ArrU.map(Tables.all(), (Table t) -> t.name());

  DatabaseConnection() {
    try {
      String s = "%s/%s?user=%s&password=%s&useSSL=%s".formatted(Constants.db_ip, Constants.db_name, Constants.db_user,
          Constants.db_pwd, Constants.db_ssl);
      System.out.println(s);
      this.connection = DriverManager.getConnection(s);
      if (!this.runMigrations()) {
        this.connection = null; // database is in a corrupt state so prevent further damage
        throw new Exception("Failed to run migrations");
      }
      System.out.println("Successfully connected to db");
    } catch (Exception e) {
      System.err.println("Could not connect to db: " + e.toString());
    }
  }

  private boolean runMigrations() {
    if (!this.tableExists(Migrations.MIGRATION_TABLE)) {
      this.query(Migrations.createMigrationTableQuery());
    }
    List<String> migrationsRan = new ArrayList<String>();
    for (Migration m : Migrations.all()) {
      Row row = this.row(Migrations.MIGRATION_TABLE, "name", m.name(), false);
      if (row == null) {
        this.query(
            "INSERT INTO `%s` (name, description, query) VALUES (?, ?, ?);".formatted(Migrations.MIGRATION_TABLE),
            new Object[] { m.name(), m.description(), m.query() });
      }
      if (row == null || !(boolean) row.g("migration_run").getKey()) {
        if (!this.query(m.query()).success()) {
          System.err.println("Error running migration: %s".formatted(m.name()));
          return false;
        }
        QueryResult result = this.query(
            "UPDATE `%s` m SET `m`.`migration_run` = TRUE WHERE `m`.`name` = ?".formatted(Migrations.MIGRATION_TABLE),
            new Object[] { m.name() });
        if (!result.success()) {
          System.err.println("Error updating migration: %s".formatted(m.name()));
          return false;
        }
        migrationsRan.add(m.name());
      }
    }
    System.out.println("Successfully ran migrations: [%s]".formatted(String.join(", ", migrationsRan)));
    return true;
  }

  /** Returns whether the input table is currenty in the connected database */
  public boolean tableExists(String table_name) {
    QueryResult result = this.query(
        "SELECT * FROM `information_schema`.`tables` WHERE `table_schema` = ? AND `table_name` = ? LIMIT 1;",
        new Object[] { Constants.db_name, table_name });
    if (!result.success()) {
      return false;
    }
    try {
      return result.result().next();
    } catch (Exception e) {
      System.err.println("Error with results when checking if table exists: " + e.toString());
    }
    return false;
  }

  /** Returns whether input table is one of the defined tables in migrations */
  public static boolean isDataTable(String table_name) {
    return Arrays.asList(DatabaseConnection.table_names).contains(table_name);
  }

  public boolean rowExists(String table_name, String column_name, Object param) {
    if (!DatabaseConnection.isDataTable(table_name)) {
      return false;
    }
    QueryResult result = this.query(
        "SELECT * FROM `%s` AS t WHERE `t`.`%s` = ? LIMIT 1;".formatted(table_name, column_name),
        new Object[] { param });
    if (!result.success()) {
      return false;
    }
    try {
      return result.result().next();
    } catch (Exception e) {
      System.err.println("Error with results when checking if table exists: " + e.toString());
    }
    return false;
  }

  public InsertResult insert(String table_name, String[] columns, Message object) {
    List<Message> objects = new ArrayList<>();
    objects.add(object);
    return this.insert(table_name, columns, objects);
  }

  public InsertResult insert(String table_name, String[] columns, Message[] objects) {
    return this.insert(table_name, columns, List.of(objects));
  }

  public InsertResult insert(String table_name, String[] columns, List<Message> objects) {
    Table t = Tables.get(table_name);
    if (!DatabaseConnection.isDataTable(table_name) || t == null || columns.length < 1 || objects.size() < 1) {
      System.err.println("Invalid insert parameters");
      return null;
    }
    for (String c : columns) {
      if (!t.hasColumn(c)) {
        System.err.println("Invalid insert column %s".formatted(c));
        return null;
      }
    }
    List<Object> params = new ArrayList<>();
    for (Message o : objects) {
      for (String c : columns) {
        FieldDescriptor descriptor = o.getDescriptorForType().findFieldByName(c);
        if (descriptor == null) {
          params.add(null);
        } else {
          params.add(o.getField(descriptor));
        }
      }
    }
    String values_string = Str.repeat(columns.length, "?", ", ");
    String values_clause = Str.repeat(objects.size(), "(%s)".formatted(values_string), ", ");
    String insert_clause = "(%s)".formatted(String.join(", ", columns));
    String query = "INSERT INTO `%s` %s VALUES %s".formatted(table_name, insert_clause, values_clause);
    QueryResult result = this.query(query, params);
    if (!result.success()) {
      return null;
    }
    List<Integer> generated_keys = new ArrayList<>();
    try {
      ResultSet rs = result.result();
      if (rs.next()) {
        generated_keys.add(rs.getInt(1));
        // because jdbc is fucking stupid we have to infer the other keys
        // https://docs.oracle.com/javadb/10.10.1.2/ref/crefjavstateautogen.html
        for (int i = 1; i < result.rows(); i++) {
          generated_keys.add(generated_keys.get(0) + i);
        }
      }
    } catch (Exception e) {
      System.err.println("Error with results when getting generated keys: " + e.toString());
    }
    return new InsertResult(result.rows(), generated_keys);
  }

  public boolean ping() {
    QueryResult result = this.query("SELECT 1;");
    return result.success();
  }

  public Row row(String table_name, String column_name, Object param) {
    return this.row(table_name, column_name, param, true);
  }

  public Row row(String table_name, String column_name, Object param, boolean check_table_name) {
    // TODO: support sort clause
    if (check_table_name && !DatabaseConnection.isDataTable(table_name)) {
      return null;
    }
    QueryResult result = this.query(
        "SELECT * FROM `%s` AS t WHERE `t`.`%s` = ? LIMIT 1;".formatted(table_name, column_name),
        new Object[] { param });
    if (!result.success()) {
      return null;
    }
    List<Row> rows = Row.formRows(result.result());
    if (rows.size() < 1) {
      return null;
    }
    return rows.get(0);
  }

  public QueryResult query(String q) {
    return this.query(q, new Object[] {});
  }

  public QueryResult query(String q, List<Object> params) {
    return this.query(q, params.toArray());
  }

  public QueryResult query(String q, Object[] params) {
    try {
      this.connection.beginRequest();
      PreparedStatement ps = this.connection.prepareStatement(q, PreparedStatement.RETURN_GENERATED_KEYS);
      int i = 1;
      for (Object param : params) {
        if (param instanceof Date) {
          ps.setTimestamp(i++, new Timestamp(((Date) param).getTime()));
        } else if (param instanceof Integer) {
          ps.setInt(i++, (Integer) param);
        } else if (param instanceof Long) {
          ps.setLong(i++, (Long) param);
        } else if (param instanceof Double) {
          ps.setDouble(i++, (Double) param);
        } else if (param instanceof Float) {
          ps.setFloat(i++, (Float) param);
        } else {
          ps.setString(i++, (String) param);
        }
      }
      System.out.println(ps.toString());
      ResultSet result = null;
      Integer rows = null;
      if (ps.execute()) {
        result = ps.getResultSet();
      } else {
        result = ps.getGeneratedKeys();
        rows = ps.getUpdateCount();
      }
      this.connection.endRequest();
      return new QueryResult(true, result, rows, "");
    } catch (Exception e) {
      System.err.println("Query `" + q + "` failed with error: " + e.toString());
      return new QueryResult(false, null, null, e.toString());
    }
  }
}
