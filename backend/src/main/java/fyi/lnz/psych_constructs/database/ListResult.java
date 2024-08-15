package fyi.lnz.psych_constructs.database;

import java.util.List;

public record ListResult(List<Row> rows, String error) {
  ListResult(String error) {
    this(error, false);
  }

  ListResult(String error, boolean print_error) {
    this(null, error);
    if (print_error) {
      System.err.println(error);
    }
  }

  ListResult(List<Row> rows) {
    this(rows, null);
  }
}
