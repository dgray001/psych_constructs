package fyi.lnz.psych_constructs.database;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("rawtypes")
public class Row {
  public Map<String, Entry<Object, Class>> row = new HashMap<String, Entry<Object, Class>>();
  public static Map<String, Class> TYPE;

  static {
    TYPE = new HashMap<String, Class>();

    TYPE.put("INT", Integer.class);
    TYPE.put("INTEGER", Integer.class);
    TYPE.put("TINYINT", Byte.class);
    TYPE.put("TINYINT UNSIGNED", Boolean.class);
    TYPE.put("SMALLINT", Short.class);
    TYPE.put("BIGINT", Long.class);
    TYPE.put("REAL", Float.class);
    TYPE.put("FLOAT", Double.class);
    TYPE.put("DOUBLE", Double.class);
    TYPE.put("DECIMAL", BigDecimal.class);
    TYPE.put("NUMERIC", BigDecimal.class);
    TYPE.put("BOOLEAN", Boolean.class);
    TYPE.put("CHAR", String.class);
    TYPE.put("VARCHAR", String.class);
    TYPE.put("LONGVARCHAR", String.class);
    TYPE.put("DATE", Date.class);
    TYPE.put("TIME", Time.class);
    TYPE.put("TIMESTAMP", Timestamp.class);
    // ...
  }

  public Row() {}

  public void add(String col, Object data, String sqlType) {
    Class c = Row.TYPE.get(sqlType);
    add(col, c.cast(data));
  }
  public <T> void add(String col, T data) {
    row.put(col, Map.entry(data, data.getClass()));
  }

  public Entry<Object, Class> g(String col) {
    return this.row.get(col);
  }

  public static List<Row> formRows(ResultSet rs) {
    List<Row> rows = new ArrayList<Row>();
    if (rs == null) {
      return rows;
    }

    try {
      ResultSetMetaData rsmd = rs.getMetaData();
      int NumOfCol = rsmd.getColumnCount();
      while(rs.next()) {
        Row row = new Row();
        for (int i = 1; i <= NumOfCol; i++) {
          row.add(rsmd.getColumnName(i), rs.getObject(i), rsmd.getColumnTypeName(i));
        }
        rows.add(row);
      }
    } catch(Exception e) {
      System.err.println("Error forming rows: " + e.toString());
    }

    return rows;
  }
}
