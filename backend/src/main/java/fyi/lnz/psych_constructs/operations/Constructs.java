package fyi.lnz.psych_constructs.operations;

import org.springframework.stereotype.Component;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.Row;
import proto.Construct;

/** Database operations for constructs */
@Component
public class Constructs implements Crud<Construct> {

  private final DatabaseConnection db;
  private final String table_name = "construct";

  public Constructs(DatabaseConnection db) {
    this.db = db;
  }

  public Construct convertRow(Row r) {
    return Construct.newBuilder()
        .setId((Integer) r.row.get("id").getKey())
        .setName((String) r.row.get("name").getKey())
        .setDescription((String) r.row.get("description").getKey())
        .build();
  }

  public Construct create(Construct c) {
    return null;
  }

  public Construct read(Long i) {
    Row r = this.db.row(this.table_name, "id", i);
    return this.convertRow(r);
  }
}
