package org.example;

import org.flywaydb.core.Flyway;
import java.sql.Connection;
import java.sql.SQLException;

public class MigrationsTest {
    public static void main(String[] args) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        Flyway flyway = Flyway.configure().dataSource("jdbc:h2:file:./src/main/resources/db/module6db", "sa", null).load();
        flyway.migrate();

        ClientService clientService = new ClientService(conn);
        //System.out.println("clientService.create(\"Nokia\") = " + clientService.create("Nokia"));
        System.out.println("clientService.getById(7) = " + clientService.getById(7));
        //clientService.setName(7, "Sony");
        //System.out.println("clientService.getById(7) = " + clientService.getById(7));
        //clientService.deleteById(6);
        System.out.println("clientService.listAll() = " + clientService.listAll());
    }
}
