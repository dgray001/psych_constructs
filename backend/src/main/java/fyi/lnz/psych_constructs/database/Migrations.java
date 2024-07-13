package fyi.lnz.psych_constructs.database;

record Migration(String name, String description, String query) {}

public class Migrations {
  static String MIGRATION_TABLE = "migrations_v1";

  static String createMigrationTableQuery() {
    return """
    CREATE TABLE `%s` (
      `id` INT NOT NULL AUTO_INCREMENT,
      `name` VARCHAR(64) NOT NULL,
      `description` VARCHAR(256) NOT NULL,
      `query` VARCHAR(5000) NOT NULL,
      `migration_run` TINYINT(1) UNSIGNED ZEROFILL NOT NULL DEFAULT FALSE,
      PRIMARY KEY (`id`),
      UNIQUE KEY `id_UNIQUE` (`id`),
      UNIQUE KEY `name_UNIQUE` (`name`)
    );
    """.formatted(Migrations.MIGRATION_TABLE);
  }

  static Migration[] all() {
    return new Migration[]{
      new Migration("name", "description", "CREATE TABLE yo_momma (id INT);")
    };
  }
}
