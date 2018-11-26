package com.automation;

import java.io.File;
import java.sql.*;
import java.util.HashMap;

public class DatabaseConnection {
    public Connection createConnection(String pathToDB, String fileName) {
        Utils utils = new Utils();
        utils.createFolder(pathToDB);
        String dbUrl = "jdbc:sqlite:" + pathToDB + File.separator + fileName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            throw new TestAutomationException("Could not create connection to DB", e.getCause());
        }
        if (conn == null) {
            throw new TestAutomationException("Connection is null");
        }
        return conn;
    }

    public HashMap<String, String> getResultAsMap(Connection connection) {
        HashMap<String, String> result = new HashMap<String, String>();
        String sql = "SELECT * \n" +
                " FROM downloadedImages\n" +
                " WHERE ID = (SELECT MAX(ID) FROM downloadedImages);";
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            result.put("id", String.valueOf(resultSet.getInt("id")));
            result.put("address", resultSet.getString("address"));
            result.put("timeOfDownload", resultSet.getString("timeOfDownload"));
            result.put("size", String.valueOf(resultSet.getInt("size")));
            return result;
        } catch (SQLException e) {
            throw new TestAutomationException("Could not execute statement", e.getCause());
        }
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new TestAutomationException("Could not close DB connection", e.getCause());
        }
    }
}
