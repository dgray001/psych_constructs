package fyi.lnz.psych_constructs.operations;

import com.google.protobuf.Message;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.InsertResult;
import fyi.lnz.psych_constructs.database.Row;
import proto.Query;

public interface Crud<T extends Message> {
  public DatabaseConnection db();

  public String tableName();

  public default String idColumn() {
    return "id";
  }

  public boolean malformed(T t);

  public boolean exists(T t);

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
    if (this.exists(t)) {
      this.error("Cannot create because object already exists");
      return null;
    }
    if (this.duplicate(t)) {
      this.error("Cannot create because object is duplicate");
      return null;
    }
    InsertResult result = this.db().insert(this.tableName(), this.insertColumns(false), t);
    try {
      return this.read(result.generated_keys().get(0));
    } catch (Exception e) {
      this.error("Error creating construct: " + e.toString());
      return null;
    }
  }

  public String[] insertColumns(boolean updateQuery);

  public default T read(Integer id) {
    Row r = this.db().row(this.tableName(), this.idColumn(), id);
    if (r == null) {
      this.error("Error reading construct");
      return null;
    }
    return this.convertRow(r);
  }

  public default T update(T t) {
    if (this.malformed(t)) {
      this.error("Cannot update because object is malformed");
      return null;
    }
    if (!this.exists(t)) {
      this.error("Cannot update because object doesn't exists");
      return null;
    }
    if (this.duplicate(t)) {
      this.error("Cannot update because object is duplicate");
      return null;
    }
    return this._update(t);
  }

  public T _update(T t);

  public default boolean delete(Integer id) {
    return this.db().delete(this.tableName(), this.idColumn(), id);
  }

  public T[] list(Query q);
}
