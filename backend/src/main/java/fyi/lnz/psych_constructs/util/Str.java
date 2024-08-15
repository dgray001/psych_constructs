package fyi.lnz.psych_constructs.util;

public class Str {
  public static String repeat(int times, String s) {
    return Str.repeat(times, s, "");
  }

  public static String repeat(int times, String s, String gap) {
    String return_string = "";
    for (int i = 0; i < times; i++) {
      if (i > 0) {
        return_string += gap;
      }
      return_string += s;
    }
    return return_string;
  }
}
