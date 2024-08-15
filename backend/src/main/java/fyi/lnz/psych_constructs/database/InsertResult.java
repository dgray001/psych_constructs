package fyi.lnz.psych_constructs.database;

import java.util.List;

public record InsertResult(Integer rows, List<Integer> generated_keys, String error) {
  public InsertResult(String error) {
    this(error, false);
  }

  public InsertResult(String error, boolean print_error) {
    this(null, null, error);
    if (print_error) {
      System.err.println(error);
    }
  }

  public InsertResult(Integer rows, List<Integer> generated_keys) {
    this(rows, generated_keys, null);
  }
}
