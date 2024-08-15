package fyi.lnz.psych_constructs.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fyi.lnz.psych_constructs.operations.Constructs;
import fyi.lnz.psych_constructs.util.Constants;
import proto.Construct;
import proto.CreateConstructRequest;
import proto.CreateConstructResponse;
import proto.ReadConstructRequest;
import proto.ReadConstructResponse;

@RestController
@RequestMapping(Constants.api_prefix + "/construct")
public class ConstructController {

  private final Constructs constructs;

  public ConstructController(Constructs constructs) {
    this.constructs = constructs;
  }

  @PostMapping(value = "/create", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] create(@RequestBody() byte[] protobuf) {
    try {
      CreateConstructRequest request = CreateConstructRequest.parseFrom(protobuf);
      Construct created = this.constructs.create(request.getConstruct());
      return CreateConstructResponse.newBuilder().setConstruct(created).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in create construct api: " + e.toString());
      return null;
    }
  }

  @PostMapping(value = "/read", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] read(@RequestBody() byte[] protobuf) {
    try {
      ReadConstructRequest request = ReadConstructRequest.parseFrom(protobuf);
      Construct created = this.constructs.read(request.getId());
      return ReadConstructResponse.newBuilder().setConstruct(created).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in read construct api: " + e.toString());
      return null;
    }
  }
}
