package fyi.lnz.psych_constructs.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.protobuf.InvalidProtocolBufferException;

import fyi.lnz.psych_constructs.controller.ApiController;
import fyi.lnz.psych_constructs.database.DatabaseConnection;
import proto.Construct;

@ApiController
public class ConstructController {

  private final DatabaseConnection db;

  public ConstructController(DatabaseConnection db) {
    this.db = db;
  }

  @PostMapping(value = "/create", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] create(@RequestBody() byte[] protobuf) throws InvalidProtocolBufferException {
    Construct request = Construct.parseFrom(protobuf);
    System.out.println(db.ping());
    return Construct.newBuilder().setName(request.getName() + "!!!").setId(request.getId() + 1).build().toByteArray();
  }
}
