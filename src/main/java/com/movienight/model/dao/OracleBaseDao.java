package com.movienight.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleBaseDao {

    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/xe";
    private static final String USER = "movienight";
    private static final String PASS = "secret";
    private static Connection conn;

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        conn = DriverManager.getConnection(URL, USER, PASS);

        return conn;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
