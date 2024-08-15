package fyi.lnz.psych_constructs.operations;

import org.springframework.stereotype.Component;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.InsertResult;
import fyi.lnz.psych_constructs.database.Row;
import proto.Construct;
import proto.Query;

/** Database operations for constructs */
@Component
public class Constructs implements Crud<Construct> {

  private final DatabaseConnection db;

  public Constructs(DatabaseConnection db) {
    this.db = db;
  }

  public DatabaseConnection db() {
    return this.db;
  }

  public String tableName() {
    return "construct";
  }

  public boolean malformed(Construct c) {
    if (c.getName() == null || c.getName().length() < 3) {
      return true;
    }
    return false;
  }

  public boolean duplicate(Construct c) {
    if (c.getId() > 0 && this.db.row(this.tableName(), "id", c.getId()) != null) {
      return true;
    }
    if (this.db.row(this.tableName(), "name", c.getName()) != null) {
      return true;
    }
    return false;
  }

  public Construct convertRow(Row r) {
    return Construct.newBuilder()
        .setId((Integer) r.row.get("id").getKey())
        .setName((String) r.row.get("name").getKey())
        .setDescription((String) r.row.get("description").getKey())
        .build();
  }

  public void _error(String e) {
  }

  public InsertResult insert(Construct c) {
    return this.db.insert(this.tableName(), new String[] { "name", "description" }, c);
  }

  public Row row(Integer id) {
    return this.db.row(this.tableName(), "id", id);
  }

  public Construct[] list(Query q) {
    return new Construct[] {};
  }
}
