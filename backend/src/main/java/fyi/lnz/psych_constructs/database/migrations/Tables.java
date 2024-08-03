package fyi.lnz.psych_constructs.database.migrations;

import java.util.ArrayList;
import java.util.List;

class Tables {
  static Table[] all() {
    return new Table[]{
      new Table(
        "construct",
        "table describing constructs which are labels for some domain of human behavior",
        1,
        new Column[]{
          new Column("id", "INT", "NOT NULL ", true, true),
          new Column("name", "VARCHAR(128)", "NOT NULL"),
          new Column("description", "VARCHAR(1024)", "NOT NULL")
        }
      ),
      new Table(
        "measure",
        "table describing measures which are models for measuring a particular construct",
        1,
        new Column[]{
          new Column("id", "INT", "NOT NULL", true, true),
          new Column("name", "VARCHAR(128)", "NOT NULL"),
          new Column("description", "VARCHAR(1024)", "NOT NULL"),
          new Column("questions_sorted", "TINYINT(1)", "UNSIGNED ZEROFILL NOT NULL DEFAULT FALSE")
        }
      ),
      new Table(
        "construct_measure",
        "table to join constructs and measures together",
        1,
        new Column[]{
          new Column("id", "INT", "NOT NULL", true, true),
          new Column("construct_id", "INT", "NOT NULL"),
          new Column("measure_id", "INT", "NOT NULL")
        }
      ),
    };
  }

  static Migration[] allMigrations() {
    Table[] tables = Tables.all();
    Migration[] migrations = new Migration[tables.length];
    for (int i = 0; i < migrations.length; i++) {
      migrations[i] = tables[i].migration();
    }
    return migrations;
  }
}

record Table(String name, String description, int version, Column[] columns) {
  String createStatement() {
    List<String> statements = new ArrayList<String>();
    for (Column column : columns) {
      statements.add(column.createStatement());
    }
    for (Column column : columns) {
      statements.addAll(column.keyStatements());
    }
    String create_body = String.join(",\n", statements);
    return "\nCREATE TABLE `%s` (\n%s\n);\n".formatted(this.name, create_body);
  }

  Migration migration() {
    if (this.version > 1) {
      System.err.println("Modifying tables is not yet supported");
    }
    return new Migration(this.name, this.description, this.createStatement());
  }
}

record Column(String name, String type, String options, boolean primary, boolean unique) {
  Column(String name, String type, String options) {
    this(name, type, options, false, false);
  }
  Column(String name, String type) {
    this(name, type, "", false, false);
  }

  public String createStatement() {
    return "`%s` %s %s".formatted(this.name, this.type, this.options);
  }

  public List<String> keyStatements() {
    List<String> key_statements = new ArrayList<String>();
    if (this.primary) {
      key_statements.add("PRIMARY KEY (`%s`)".formatted(this.name));
    }
    if (this.unique) {
      key_statements.add("UNIQUE KEY `%s_UNIQUE` (`%s`)".formatted(this.name, this.name));
    }
    return key_statements;
  }
}
