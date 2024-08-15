package fyi.lnz.psych_constructs.operations;

import org.springframework.stereotype.Component;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.InsertResult;
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
    // TODO: add check for malformed construct
    // TODO: add check for duplicate construct
    InsertResult result = db.insert("construct", new String[] { "name", "description" }, c);
    try {
      return this.read(result.generated_keys().get(0));
    } catch (Exception e) {
      System.err.println("Error creating construct: " + e.toString());
      return null;
    }
  }

  public Construct read(Integer i) {
    Row r = this.db.row(this.table_name, "id", i);
    if (r == null) {
      System.err.println("Error reading construct");
      return null;
    }
    return this.convertRow(r);
  }
}
