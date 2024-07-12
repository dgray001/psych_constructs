package fyi.lnz.psych_constructs.database;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Service;

@Service
public class DatabaseConnection {
  Connection connection;

  DatabaseConnection() {
    try {
      this.connection = DriverManager.getConnection(
        "jdbc:mariadb://localhost:3306/psych_constructs",
        "yodan", "Sjf@85882"
      );
      System.out.println("Successfully connected to db");
    } catch(Exception e) {
      System.err.println("Could not connect to db: " + e.toString());
    }
  }
}
