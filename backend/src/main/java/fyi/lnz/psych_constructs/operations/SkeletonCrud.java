package fyi.lnz.psych_constructs.operations;

import org.springframework.stereotype.Component;

import com.google.protobuf.Message;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.InsertResult;
import fyi.lnz.psych_constructs.database.Row;
import proto.Query;

/** Database operations for constructs */
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

  public boolean duplicate(Message c) {
    return false;
  }

  public Message convertRow(Row r) {
    return null;
  }

  public void _error(String e) {
  }

  public InsertResult insert(Message c) {
    return null;
  }

  public Message[] list(Query q) {
    return new Message[] {};
  }
}
