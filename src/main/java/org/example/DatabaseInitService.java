package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DatabaseInitService {
    private static final String INIT_DB_FILE = "sql/init_db.sql";

    public static void main(String[] args) throws SQLException {
        try (InputStream fis = new FileInputStream(INIT_DB_FILE);
             Scanner scanner = new Scanner(fis);
             Statement stmt = Database.getInstance().getConnection().createStatement()) {
            StringBuilder query = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    query.append(line);
                    if (line.endsWith(";")) {
                        stmt.executeUpdate(query.toString());
                        query.setLength(0);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}