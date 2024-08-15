package fyi.lnz.psych_constructs.operations;

import com.google.protobuf.Message;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.InsertResult;
import fyi.lnz.psych_constructs.database.Row;
import proto.Query;

public interface Crud<T extends Message> {
  public DatabaseConnection db();

  public String tableName();

  public boolean malformed(T t);

  public boolean duplicate(T t);

  public T convertRow(Row r);

  public default void error(String e) {
    System.err.println(e);
    this._error(e);
  }

  public void _error(String e);

  public default T create(T t) {
    if (this.malformed(t)) {
      this.error("Cannot create because object is malformed");
      return null;
    }
    if (this.duplicate(t)) {
      this.error("Cannot create because object is duplicate");
      return null;
    }
    InsertResult result = this.insert(t);
    try {
      return this.read(result.generated_keys().get(0));
    } catch (Exception e) {
      this.error("Error creating construct: " + e.toString());
      return null;
    }
  }

  public InsertResult insert(T t);

  public default T read(Integer id) {
    Row r = this.db().row(this.tableName(), "id", id);
    if (r == null) {
      this.error("Error reading construct");
      return null;
    }
    return this.convertRow(r);
  }

  public T[] list(Query q);

  // public T update(T t);

  // public T delete(Integer id);
}
