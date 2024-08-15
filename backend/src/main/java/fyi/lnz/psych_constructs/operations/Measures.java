package fyi.lnz.psych_constructs.operations;

import org.springframework.stereotype.Component;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.Row;
import fyi.lnz.psych_constructs.database.UpdateResult;
import proto.Measure;
import proto.Query;

/** Database operations for measures */
@Component
public class Measures implements Crud<Measure> {

  private final DatabaseConnection db;

  public Measures(DatabaseConnection db) {
    this.db = db;
  }

  public DatabaseConnection db() {
    return this.db;
  }

  public String tableName() {
    return "measure";
  }

  public boolean malformed(Measure m) {
    if (m.getName() == null || m.getName().length() < 3) {
      return true;
    }
    return false;
  }

  public boolean exists(Measure m) {
    if (m.getId() > 0 && this.db.rowExists(this.tableName(), this.idColumn(), m.getId())) {
      return true;
    }
    return false;
  }

  public boolean duplicate(Measure m) {
    if (this.db.rowExists(this.tableName(), "name", m.getName(), this.idColumn(), m.getId())) {
      return true;
    }
    return false;
  }

  public Measure convertRow(Row r) {
    return Measure.newBuilder()
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

  public Measure _update(Measure m) throws Exception {
    UpdateResult result = this.db().update(this.tableName(), this.idColumn(), m.getId(), this.insertColumns(true), m);
    if (result == null || result.rows() < 1) {
      return null;
    }
    return this.read(m.getId());
  }

  public Measure[] list(Query q) {
    return new Measure[] {};
  }
}
