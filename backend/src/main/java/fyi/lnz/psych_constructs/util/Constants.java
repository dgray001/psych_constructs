package fyi.lnz.psych_constructs.util;

enum APP_ENVIRONMENT {
  DEV, PROD;
}

public class Constants {
  public static APP_ENVIRONMENT getEnvironment() {
    String s = System.getenv("ENVIRONMENT");
    try {
      return APP_ENVIRONMENT.valueOf(s);
    } catch(Exception e) {
      return APP_ENVIRONMENT.DEV;
    }
  }

  public static boolean isEnvironment(APP_ENVIRONMENT e) {
    return Constants.getEnvironment() == e;
  }
  public static boolean isDev() {
    return Constants.isEnvironment(APP_ENVIRONMENT.DEV);
  }
  public static boolean isProd() {
    return Constants.isEnvironment(APP_ENVIRONMENT.PROD);
  }

  public static String db_ip = isDev() ?
    "jdbc:mariadb://localhost:3306" :
    "jdbc:mysql:///162.241.24.125:3306" ;

  public static String db_name = isDev() ?
    "psych_constructs" :
    "lnzfyi_psych_constructs" ;

  public static String db_user = isDev() ? "lnzfyi_yodan" : "yodan" ;

  public static String db_pwd = isDev() ?
    "Sjf@85882" :
    "lnzfyi_psych_constructs" ;

  public static String db_ssl = isDev() ? "false" : "true" ;
}
