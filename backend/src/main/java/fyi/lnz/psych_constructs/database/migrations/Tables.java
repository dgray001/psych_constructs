package fyi.lnz.psych_constructs.database.migrations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tables {
  private static Map<String, Table> table_hash = Tables.getHash();

  public static Table[] all() {
    return new Table[] {
        new Table(
            "construct",
            "table describing constructs which are labels for some domain of human behavior",
            1,
            new Column[] {
                new Column("id", "INT", "AUTO_INCREMENT", true, true),
                new Column("name", "VARCHAR(128)", "", true),
                new Column("description", "VARCHAR(1024)", "DEFAULT ''")
            }),
        new Table(
            "measure",
            "table describing measures which are models for measuring a particular construct",
            1,
            new Column[] {
                new Column("id", "INT", " AUTO_INCREMENT", true, true),
                new Column("name", "VARCHAR(128)", "", true),
                new Column("description", "VARCHAR(1024)", "DEFAULT ''"),
                new Column("questions_sorted", "TINYINT(1) UNSIGNED ZEROFILL", "DEFAULT FALSE")
            }),
        new Table(
            "construct_measure",
            "table to join constructs and measures together",
            1,
            new Column[] {
                new Column("id", "INT", "AUTO_INCREMENT", true, true),
                new Column("construct_id", "INT", ""),
                new Column("measure_id", "INT", "")
            }),
    };
  }

  private static Map<String, Table> getHash() {
    Map<String, Table> hash = new HashMap<>();
    for (Table t : Tables.all()) {
      hash.put(t.name(), t);
    }
    return hash;
  };

  public static Table get(String name) {
    return Tables.table_hash.get(name);
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

record Column(String name, String type, String options, boolean unique, boolean primary) {
  Column(String name, String type, String options, boolean unique) {
    this(name, type, options, unique, false);
  }

  Column(String name, String type, String options) {
    this(name, type, options, false, false);
  }

  Column(String name, String type) {
    this(name, type, "", false, false);
  }

  public String createStatement() {
    return "`%s` %s %s".formatted(this.name, this.type, "NOT NULL %s".formatted(this.options));
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
