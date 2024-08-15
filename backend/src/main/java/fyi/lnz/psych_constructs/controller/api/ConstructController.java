package fyi.lnz.psych_constructs.controller.api;

import java.util.Arrays;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fyi.lnz.psych_constructs.operations.Constructs;
import fyi.lnz.psych_constructs.operations.Crud.ListResult;
import fyi.lnz.psych_constructs.util.Constants;
import proto.Construct;
import proto.CreateConstructRequest;
import proto.CreateConstructResponse;
import proto.DeleteConstructRequest;
import proto.DeleteConstructResponse;
import proto.ListConstructRequest;
import proto.ListConstructResponse;
import proto.ReadConstructRequest;
import proto.ReadConstructResponse;
import proto.UpdateConstructRequest;
import proto.UpdateConstructResponse;

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
      return CreateConstructResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/read", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] read(@RequestBody() byte[] protobuf) {
    try {
      ReadConstructRequest request = ReadConstructRequest.parseFrom(protobuf);
      Construct read = this.constructs.read(request.getId());
      return ReadConstructResponse.newBuilder().setConstruct(read).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in read construct api: " + e.toString());
      return ReadConstructResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/update", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] update(@RequestBody() byte[] protobuf) {
    try {
      UpdateConstructRequest request = UpdateConstructRequest.parseFrom(protobuf);
      Construct updated = this.constructs.update(request.getConstruct());
      return UpdateConstructResponse.newBuilder().setConstruct(updated).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in update construct api: " + e.toString());
      return UpdateConstructResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/delete", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] delete(@RequestBody() byte[] protobuf) {
    try {
      DeleteConstructRequest request = DeleteConstructRequest.parseFrom(protobuf);
      this.constructs.delete(request.getId());
      return DeleteConstructResponse.newBuilder().build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in update construct api: " + e.toString());
      return DeleteConstructResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/list", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] list(@RequestBody() byte[] protobuf) {
    try {
      ListConstructRequest request = ListConstructRequest.parseFrom(protobuf);
      ListResult<Construct> result = this.constructs.list(request.getQuery());
      if (!result.success()) {
        return ListConstructResponse.newBuilder().setErrorMessage(result.error()).build().toByteArray();
      }
      return ListConstructResponse.newBuilder().addAllConstructs(Arrays.asList(result.rows())).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in list constructs api: " + e.toString());
      return ListConstructResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }
}
