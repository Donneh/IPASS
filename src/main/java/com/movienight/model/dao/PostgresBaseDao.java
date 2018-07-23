package com.movienight.model.dao;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class PostgresBaseDao {

    private Connection conn;

    public Connection getConnection() {
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");

            conn = ds.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return conn;
    }

    protected void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
