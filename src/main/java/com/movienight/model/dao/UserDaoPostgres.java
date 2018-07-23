package com.movienight.model.dao;

import com.movienight.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoPostgres extends PostgresBaseDao {

    /**
     * Insert a user into the database.
     * @param user
     * @return User | null
     * @throws SQLException
     */
    public User save(User user) {
        try(Connection conn = getConnection()) {
            String query =
                    "INSERT INTO users " +
                            "(id, username) VALUES " +
                            "(?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
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
        try(Connection conn = getConnection()) {
            String query = "SELECT id, username FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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
        try(Connection conn = getConnection()) {
            String query = "SELECT id, username FROM users";
            Statement stmt = conn.createStatement();

            ResultSet result = stmt.executeQuery(query);
            List<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(buildUserObject(result));
            }
            closeConnection();
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
        try(Connection conn = getConnection()) {
            String query =
                    "UPDATE users SET " +
                    "username = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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
        try(Connection conn = getConnection()) {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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
     * Checks if the user has watched the given movie.
     * @param movie_id
     * @return bool
     */
    public boolean hasWatched(int movie_id) {
        try(Connection conn = getConnection()) {
            String query = "SELECT user_id, movie_id FROM USER_MOVIE WHERE user_id = 1 AND movie_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, movie_id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return  true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Marks movie as watched for the specified user.
     * @param movie_id
     * @return bool
     */
    public boolean markWatched(int movie_id) {
        try(Connection conn = getConnection()) {
            String query = "INSERT INTO USER_MOVIE (USER_ID, MOVIE_ID) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, 1);
            stmt.setInt(2, movie_id);

            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0) {
                return  true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Marks movie as not watched for specified user.
     * @param movie_id
     * @return bool
     */
    public boolean markNotWatched(int movie_id) {
        try(Connection conn = getConnection()) {
            String query = "DELETE FROM USER_MOVIE WHERE USER_ID = 1 AND MOVIE_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, movie_id);

            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0) {
                return  true;
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