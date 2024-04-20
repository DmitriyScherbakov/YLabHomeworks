package org.example.Database;

import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Liquibase {
    private static final String URL = "jdbc:postgresql://localhost:5432/TrainingDiary?currentSchema=service_tables";

    private static final String USER_NAME = "DmitriyScherbakov";

    private static final String PASSWORD = "scherbakov";

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase.Liquibase liquibase =
                    new liquibase.Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        } catch (SQLException | LiquibaseException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }
}
