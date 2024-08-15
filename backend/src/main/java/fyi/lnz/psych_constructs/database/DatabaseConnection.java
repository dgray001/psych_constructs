package fyi.lnz.psych_constructs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.protobuf.Message;

import fyi.lnz.psych_constructs.database.migrations.*;
import fyi.lnz.psych_constructs.util.ArrU;
import fyi.lnz.psych_constructs.util.Constants;

record QueryResult(boolean success, ResultSet result, String error) {
}

@Service
public class DatabaseConnection {
  private Connection connection;
  private String[] table_names = ArrU.map(Tables.all(), (Table t) -> t.name());

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
      Row row = this.row(Migrations.MIGRATION_TABLE, "name", m.name());
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

  public boolean tableExists(String tableName) {
    QueryResult result = this.query(
        "SELECT * FROM `information_schema`.`tables` WHERE `table_schema` = ? AND `table_name` = ? LIMIT 1;",
        new Object[] { Constants.db_name, tableName });
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

  public boolean rowExists(String tableName, String columnName, Object param) {
    QueryResult result = this.query(
        "SELECT * FROM `%s` AS t WHERE `t`.`%s` = ? LIMIT 1;".formatted(tableName, columnName),
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

  public void insert(String table_name, String[] columns, List<Message> objects) {
    if (columns.length < 1 || objects.size() < 1) {
      return;
    }
    List<String> object_strings = new ArrayList<>();
    for (Message m : objects) {
      object_strings.add(this.objectToInsertStatement(columns, m));
    }
    String values_clause = String.join(", ", object_strings);
    String insert_clause = "(%s)".formatted(String.join(", ", columns));
    String query = "INSERT INTO `%s` %s VALUES %s".formatted(table_name, insert_clause, values_clause);

  }

  private String objectToInsertStatement(String[] columns, Message object) {
    List<String> columns_strings = new ArrayList<>();
    return "(%s)".formatted(String.join(", ", columns_strings));
  }

  public boolean ping() {
    QueryResult result = this.query("SELECT 1;");
    return result.success();
  }

  public Row row(String table_name, String column_name, Object param) {
    QueryResult result = this.query(
        "SELECT * FROM `%s` AS t WHERE `t`.`%s` = ? LIMIT 1;".formatted(table_name, column_name),
        new Object[] { param });
    if (!result.success()) {
      return null;
    }
    List<Row> rows = Row.formRows(result.result());
    if (rows.size() < 1) {
      return null;
    } else if (rows.size() > 1) {
      // option to return something other than first one?
    }
    return rows.get(0);
  }

  public QueryResult query(String q) {
    return this.query(q, new Object[] {});
  }

  public QueryResult query(String q, Object[] params) {
    try {
      this.connection.beginRequest();
      PreparedStatement ps = this.connection.prepareStatement(q);
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
      ps.execute();
      ResultSet result = ps.getResultSet();
      this.connection.endRequest();
      return new QueryResult(true, result, "");
    } catch (Exception e) {
      System.err.println("Query `" + q + "` failed with error: " + e.toString());
      return new QueryResult(false, null, e.toString());
    }
  }
}
