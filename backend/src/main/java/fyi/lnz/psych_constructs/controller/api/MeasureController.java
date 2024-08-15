package fyi.lnz.psych_constructs.controller.api;

import java.util.Arrays;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fyi.lnz.psych_constructs.operations.Crud.ListResult;
import fyi.lnz.psych_constructs.operations.Measures;
import fyi.lnz.psych_constructs.util.Constants;
import proto.Measure;
import proto.CreateMeasureRequest;
import proto.CreateMeasureResponse;
import proto.DeleteMeasureRequest;
import proto.DeleteMeasureResponse;
import proto.ListMeasureRequest;
import proto.ListMeasureResponse;
import proto.ReadMeasureRequest;
import proto.ReadMeasureResponse;
import proto.UpdateMeasureRequest;
import proto.UpdateMeasureResponse;

@RestController
@RequestMapping(Constants.api_prefix + "/measure")
public class MeasureController {

  private final Measures measures;

  public MeasureController(Measures measures) {
    this.measures = measures;
  }

  @PostMapping(value = "/create", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] create(@RequestBody() byte[] protobuf) {
    try {
      CreateMeasureRequest request = CreateMeasureRequest.parseFrom(protobuf);
      Measure created = this.measures.create(request.getMeasure());
      return CreateMeasureResponse.newBuilder().setMeasure(created).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in create measure api: " + e.toString());
      return CreateMeasureResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/read", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] read(@RequestBody() byte[] protobuf) {
    try {
      ReadMeasureRequest request = ReadMeasureRequest.parseFrom(protobuf);
      Measure read = this.measures.read(request.getId());
      return ReadMeasureResponse.newBuilder().setMeasure(read).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in read measure api: " + e.toString());
      return ReadMeasureResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/update", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] update(@RequestBody() byte[] protobuf) {
    try {
      UpdateMeasureRequest request = UpdateMeasureRequest.parseFrom(protobuf);
      Measure updated = this.measures.update(request.getMeasure());
      return UpdateMeasureResponse.newBuilder().setMeasure(updated).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in update measure api: " + e.toString());
      return UpdateMeasureResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/delete", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] delete(@RequestBody() byte[] protobuf) {
    try {
      DeleteMeasureRequest request = DeleteMeasureRequest.parseFrom(protobuf);
      this.measures.delete(request.getId());
      return DeleteMeasureResponse.newBuilder().build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in update measure api: " + e.toString());
      return DeleteMeasureResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }

  @PostMapping(value = "/list", consumes = "application/x-protobuf", produces = "application/x-protobuf")
  public byte[] list(@RequestBody() byte[] protobuf) {
    try {
      ListMeasureRequest request = ListMeasureRequest.parseFrom(protobuf);
      ListResult<Measure> result = this.measures.list(request.getQuery());
      if (!result.success()) {
        return ListMeasureResponse.newBuilder().setErrorMessage(result.error()).build().toByteArray();
      }
      return ListMeasureResponse.newBuilder().addAllMeasures(Arrays.asList(result.rows())).build().toByteArray();
    } catch (Exception e) {
      System.err.println("Error in list measures api: " + e.toString());
      return ListMeasureResponse.newBuilder().setErrorMessage(e.toString()).build().toByteArray();
    }
  }
}
