package fyi.lnz.psych_constructs.database.migrations;

import java.util.stream.Stream;

public class Migrations {
  public static String MIGRATION_TABLE = "migrations_v1";

  public static String createMigrationTableQuery() {
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

  public static Migration[] all() {
    Migration[] table_migrations = Tables.allMigrations();
    return Stream.of(
      table_migrations
    ).flatMap(Stream::of).toArray(Migration[]::new);
  }
}
