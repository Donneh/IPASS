package com.movienight.model.dao;

import com.movienight.model.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDaoPostgres extends PostgresBaseDao {

    /**
     * Save genre to the database.
     * @param genre
     * @return Genre | null
     */
    public Genre save(Genre genre) {
        try(Connection conn = getConnection()) {
            String query = "INSERT INTO genres (name) VALUES (?)";
            String generatedColumns[] = { "id" };
            PreparedStatement stmt = conn.prepareStatement(query, generatedColumns);
            stmt.setString(1, genre.getName());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                return find(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Find genre with specified id.
     * @param id
     * @return Genre | null
     */
    public Genre find(int id) {
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM genres WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            Genre genre = null;
            if(result.next()) {
                genre = buildGenreObject(result);
            }
            return genre;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Find genre with specified name.
     * @param name
     * @return Genre | null
     */
    public Genre findByName(String name) {
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM genres WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildGenreObject(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all genres from the database.
     * @return List
     */
    public List<Genre> index() {
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM genres";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            List<Genre> genres = new ArrayList<>();
            while(result.next()) {
                genres.add(buildGenreObject(result));
            }
            return genres;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all genres from the given movie.
     * @param id
     * @return List
     */
    public List<Genre> findByMovie(int id) {
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM MOVIE_GENRE WHERE movie_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            List<Genre> genres = new ArrayList<>();
            while (result.next()) {
                genres.add(find(result.getInt("GENRE_ID")));
            }
            return genres;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Build Genre object from database result.
     * @param rs
     * @return Genre
     * @throws SQLException
     */
    private Genre buildGenreObject(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }

}