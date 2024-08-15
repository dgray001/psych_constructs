package fyi.lnz.psych_constructs.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.InvalidProtocolBufferException;

import fyi.lnz.psych_constructs.database.DatabaseConnection;
import fyi.lnz.psych_constructs.database.InsertResult;
import fyi.lnz.psych_constructs.operations.Constructs;
import fyi.lnz.psych_constructs.util.Constants;
import proto.Construct;

@RestController
@RequestMapping(Constants.api_prefix + "/construct")
public class ConstructController {

  private final DatabaseConnection db;
  private final Constructs constructs;

  public ConstructController(DatabaseConnection db, Constructs constructs) {
    this.db = db;
    this.constructs = constructs;
  }

  @PostMapping(value = "/create", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] create(@RequestBody() byte[] protobuf) throws InvalidProtocolBufferException {
    Construct request = Construct.parseFrom(protobuf);
    InsertResult result = db.insert("construct", new String[] { "name", "description" },
        new Construct[] { request,
            Construct.newBuilder(request).setName("%s 2".formatted(request.getName())).build() });
    if (result != null) {
      System.out.println(result.rows() + " " + result.generated_keys());
      System.out.println(this.constructs.read(result.generated_keys().get(0)));
    }
    return Construct.newBuilder().setName(request.getName() + "!!!").setId(request.getId() + 1).build().toByteArray();
  }
}
