package com.movienight.model.dao;

import com.movienight.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoOracle extends OracleBaseDao {

    /**
     * Insert a user into the database.
     * @param user
     * @return User | null
     * @throws SQLException
     */
    public User save(User user) {
        try {
            String query =
                    "INSERT INTO users " +
                            "(id, username) VALUES " +
                            "(?, ?)";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1,user.getId());
            stmt.setString(2, user.getUsername());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Find a user from the database by the given id.
     * @param id
     * @return User | null
     * @throws SQLException
     */
    public User find(int id) {
        try {
            String query = "SELECT id, username FROM users WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildUserObject(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve all users from the database.
     * @return List<User> | null
     * @throws SQLException
     */
    public List<User> index()  {
        try {
            String query = "SELECT id, username FROM users";
            Statement stmt = getConnection().createStatement();

            ResultSet result = stmt.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(buildUserObject(result));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update the user with the given id.
     * @param user
     * @return User | null
     * @throws SQLException
     */
    public User update(User user) {
        try {

            String query =
                    "UPDATE users SET " +
                    "username = ? WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setInt(2, user.getId());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Deletes user with specified id from the database.
     * @param user
     * @return bool
     */
    public boolean delete(User user) {
        try {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, user.getId());

            int result = stmt.executeUpdate();
            if(result > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method builds a user object from the database result.
     * @param rs
     * @return
     * @throws SQLException
     */
    private User buildUserObject(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("username"));
    }
}