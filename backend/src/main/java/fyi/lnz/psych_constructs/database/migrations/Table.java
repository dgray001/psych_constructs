package fyi.lnz.psych_constructs.database.migrations;

import java.util.ArrayList;
import java.util.List;

public record Table(String name, String description, int version, Column[] columns) {
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

  public boolean hasColumn(String column) {
    for (Column c : this.columns) {
      if (c.name() == column) {
        return true;
      }
    }
    return false;
  }
}
