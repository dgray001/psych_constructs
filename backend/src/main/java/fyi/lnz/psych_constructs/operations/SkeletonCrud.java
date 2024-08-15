package fyi.lnz.psych_constructs.operations;

import org.springframework.stereotype.Component;

import com.google.protobuf.Message;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.Row;
import proto.Query;

/** Use to quickly generate boilerplate for new objects */
@Component
public class SkeletonCrud implements Crud<Message> {

  private final DatabaseConnection db;

  public SkeletonCrud(DatabaseConnection db) {
    this.db = db;
  }

  public DatabaseConnection db() {
    return this.db;
  }

  public String tableName() {
    return "";
  }

  public boolean malformed(Message c) {
    return false;
  }

  public boolean exists(Message c) {
    return false;
  }

  public boolean duplicate(Message c) {
    return false;
  }

  public Message convertRow(Row r) {
    return null;
  }

  public void _error(String e) {
  }

  public String[] insertColumns(boolean updateQuery) {
    if (updateQuery) {
      return new String[] {};
    }
    return new String[] {};
  }

  public Message _update(Message c) {
    return null;
  }

  public ListResult<Message> list(Query q) {
    return null;
  }
}
