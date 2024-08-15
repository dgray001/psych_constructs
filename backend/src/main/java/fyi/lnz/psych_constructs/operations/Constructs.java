package fyi.lnz.psych_constructs.operations;

import org.springframework.stereotype.Component;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.InsertResult;
import fyi.lnz.psych_constructs.database.Row;
import fyi.lnz.psych_constructs.database.UpdateResult;
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

  public boolean exists(Construct c) {
    if (c.getId() > 0 && this.db.rowExists(this.tableName(), this.idColumn(), c.getId())) {
      return true;
    }
    return false;
  }

  public boolean duplicate(Construct c) {
    if (this.db.rowExists(this.tableName(), "name", c.getName(), this.idColumn(), c.getId())) {
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

  public String[] insertColumns(boolean updateQuery) {
    if (updateQuery) {
      return new String[] { this.idColumn(), "name", "description" };
    }
    return new String[] { "name", "description" };
  }

  public Row row(Integer id) {
    return this.db().row(this.tableName(), this.idColumn(), id);
  }

  public Construct _update(Construct c) {
    UpdateResult result = this.db().update(this.tableName(), this.idColumn(), c.getId(), this.insertColumns(true), c);
    if (result == null || result.rows() < 1) {
      return null;
    }
    return this.read(c.getId());
  }

  public Construct[] list(Query q) {
    return new Construct[] {};
  }
}
