package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createSt;
    private PreparedStatement readIdSt;
    private PreparedStatement readNameSt;
    private PreparedStatement setNameSt;
    private PreparedStatement delIdSt;
    private Statement readAllSt;

    public ClientService(Connection connection) {
        try {
            createSt = connection.prepareStatement("INSERT INTO client (name) VALUES (?);");
            readIdSt = connection.prepareStatement("SELECT id FROM client WHERE name = ?;");
            readNameSt = connection.prepareStatement("SELECT name FROM client WHERE id = ?;");
            setNameSt = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?;");
            delIdSt = connection.prepareStatement("DELETE FROM client WHERE id = ?;");
            readAllSt = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long create(String name) throws SQLException {
        createSt.setString(1, name);
        createSt.executeUpdate();
        readIdSt.setString(1, name);
        ResultSet rs = readIdSt.executeQuery();
        if (!rs.next()) {
            return Long.parseLong(null);
        }
        return rs.getLong("id");
    }

    public String getById(long id) throws SQLException {
        readNameSt.setLong(1, id);
        ResultSet rs = readNameSt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        return rs.getString("name");
    }

    public void setName(long id, String name) throws SQLException {
        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);
        setNameSt.addBatch();
        setNameSt.executeBatch();
    }

    public void deleteById(long id) throws SQLException {
        delIdSt.setLong(1, id);
        setNameSt.executeUpdate();
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
