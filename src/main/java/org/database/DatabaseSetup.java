package org.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseSetup {
    public static Connection setupConnection() throws Exception {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "misho123";

        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(jdbcUrl, username, password);
    }
}
