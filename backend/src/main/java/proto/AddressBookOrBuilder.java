// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: test.proto
// Protobuf Java Version: 4.27.2

package proto;

public interface AddressBookOrBuilder extends
    // @@protoc_insertion_point(interface_extends:proto.AddressBook)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated .proto.Person people = 1;</code>
   */
  java.util.List<proto.Person> 
      getPeopleList();
  /**
   * <code>repeated .proto.Person people = 1;</code>
   */
  proto.Person getPeople(int index);
  /**
   * <code>repeated .proto.Person people = 1;</code>
   */
  int getPeopleCount();
  /**
   * <code>repeated .proto.Person people = 1;</code>
   */
  java.util.List<? extends proto.PersonOrBuilder> 
      getPeopleOrBuilderList();
  /**
   * <code>repeated .proto.Person people = 1;</code>
   */
  proto.PersonOrBuilder getPeopleOrBuilder(
      int index);
}
