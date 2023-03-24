package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final PreparedStatement createSt;
    private final PreparedStatement readIdSt;
    private final PreparedStatement readNameSt;
    private final PreparedStatement setNameSt;
    private final PreparedStatement delIdSt;
    private final Statement readAllSt;

    public ClientService(Connection connection) {
        try {
            createSt = connection.prepareStatement("INSERT INTO client (name) VALUES (?);");
            readIdSt = connection.prepareStatement("SELECT MAX(id) FROM client WHERE name = ?;");
            readNameSt = connection.prepareStatement("SELECT name FROM client WHERE id = ?;");
            setNameSt = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?;");
            delIdSt = connection.prepareStatement("DELETE FROM client WHERE id = ?;");
            readAllSt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long create(String name) throws SQLException {
        if (name.length() < 2 || name.length() > 1000) {
            throw new IllegalArgumentException("To short or to long name");
        }
        createSt.setString(1, name);
        createSt.executeUpdate();
        readIdSt.setString(1, name);
        ResultSet rs = readIdSt.executeQuery();
        if (!rs.next()) {
            return Long.parseLong(null);
        }
        return rs.getLong("MAX(id)");
    }

    public String getById(long id) throws SQLException {
        readNameSt.setLong(1, id);
        ResultSet rs = readNameSt.executeQuery();
        if (!rs.next()) {
            throw new IllegalArgumentException("There is no such ID in the table");
        }
        return rs.getString("name");
    }

    public void setName(long id, String name) throws SQLException {
        getById(id);
        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);
        setNameSt.addBatch();
        setNameSt.executeBatch();
    }

    public void deleteById(long id) throws SQLException {
        getById(id);
        delIdSt.setLong(1, id);
        delIdSt.executeUpdate();
    }

    public List<ClientDataDto> listAll() throws SQLException {
        List<ClientDataDto> clientLines = new ArrayList<>();
        ResultSet rs = readAllSt.executeQuery("SELECT id, name FROM client;");
        while (rs.next()) {
            clientLines.add(new ClientDataDto(rs.getLong("id"), rs.getString("name")));
        }
        return clientLines;
    }
}
