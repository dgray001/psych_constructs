package fyi.lnz.psych_constructs.database;

public record UpdateResult(Integer rows, String error) {
  public UpdateResult(String error) {
    this(error, false);
  }

  public UpdateResult(String error, boolean print_error) {
    this(null, error);
    if (print_error) {
      System.err.println(error);
    }
  }

  public UpdateResult(Integer rows) {
    this(rows, null);
  }
}
