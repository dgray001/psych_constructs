package fyi.lnz.psych_constructs.util;

enum APP_ENVIRONMENT {
  DEV, PROD;
}

public class Constants {
  public final static APP_ENVIRONMENT getEnvironment() {
    String s = System.getenv("ENVIRONMENT");
    try {
      return APP_ENVIRONMENT.valueOf(s);
    } catch (Exception e) {
      return APP_ENVIRONMENT.DEV;
    }
  }

  public final static boolean isEnvironment(APP_ENVIRONMENT e) {
    return Constants.getEnvironment() == e;
  }

  public final static boolean isDev() {
    return Constants.isEnvironment(APP_ENVIRONMENT.DEV);
  }

  public final static boolean isProd() {
    return Constants.isEnvironment(APP_ENVIRONMENT.PROD);
  }

  public final static String db_ip = isDev() ? "jdbc:mariadb://localhost:3306" : "jdbc:mysql://162.241.24.125:3306";

  public final static String db_name = isDev() ? "psych_constructs" : "lnzfyi_psych_constructs";

  public final static String db_user = isDev() ? "yodan" : "lnzfyi_yodan";

  public final static String db_pwd = "Sjf@85882";

  public final static String db_ssl = isDev() ? "false" : "false";

  public final static String api_prefix = "api";

  public final static Integer default_query_limit = 50;

  public final static String search_delimiter = "⏢";
}
