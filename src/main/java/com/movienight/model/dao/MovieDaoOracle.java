package com.movienight.model.dao;

import com.movienight.model.Event;
import com.movienight.model.Genre;
import com.movienight.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoOracle extends OracleBaseDao {

    private GenreDaoOracle genreDaoOracle;

    public MovieDaoOracle() {
        this.genreDaoOracle = new GenreDaoOracle();
    }

    /**
     * Save a movie tot he database.
     * @param movie
     * @return Movie | null
     */
    public Movie save(Movie movie) {
        try {
            String query =
                    "INSERT INTO movies " +
                            "(title, synopsis, poster, release_date, created_at) " +
                            "VALUES (?, ?, ?, ?, ?)";
            String generatedColumns[] = { "id" };
            PreparedStatement stmt = getConnection().prepareStatement(query, generatedColumns);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getSynopsis());
            stmt.setString(3, movie.getPoster());
            stmt.setDate(4, movie.getRelease_date());
            stmt.setDate(5, new Date(System.currentTimeMillis()));

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                movie.setId(rs.getInt(1));
                if(addGenresToMovie(movie)) {
                    return find(movie.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean addGenresToMovie(Movie movie) throws SQLException{
        for (Genre genre : movie.getGenres()) {
            String genreQuery = "INSERT INTO MOVIE_GENRE (MOVIE_ID, GENRE_ID) VALUES (?, ?)";
            PreparedStatement pstmt = getConnection().prepareStatement(genreQuery);
            pstmt.setInt(1, movie.getId());
            pstmt.setInt(2, genre.getId());
            int affectedRows = pstmt.executeUpdate();
            if(!(affectedRows > 0)) {
                if(delete(movie)) {
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * Find a movie with the given id.
     * @param id
     * @return Movie | null
     */
    public Movie find(int id) {
        try {
            String query = "SELECT id, title, synopsis, poster, release_date, created_at, updated_at FROM movies WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Movie movie = buildMovieObject(result);
                movie.setGenres(genreDaoOracle.findByMovie(id));
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve all movies from the database.
     * @return List | null
     */
    public List<Movie> index() {
        try {
            String query = "SELECT * FROM movies";
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery(query);
            List<Movie> movies = new ArrayList<>();
            while (result.next()) {
                Movie movie = buildMovieObject(result);
                movie.setGenres(genreDaoOracle.findByMovie(movie.getId()));
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve movies that match the given searchQuery;
     * @param searchQuery
     * @return List | null
     */
    public List<Movie> search(String searchQuery) {
        try {
            String query = "SELECT * FROM movies WHERE title LIKE ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, '%' + searchQuery + '%');
            ResultSet result = stmt.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (result.next()) {
                Movie movie = buildMovieObject(result);
                movie.setGenres(genreDaoOracle.findByMovie(movie.getId()));
                movies.add(movie);
            }
            return movies;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update the movie.
     * @param movie
     * @return Movie | mull
     */
    public Movie update(Movie movie) {
        try {
            String query =
                    "UPDATE movies SET " +
                    "title = ?, synopsis = ?, release_date = ?, poster = ?, updated_at = ? " +
                    "WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getSynopsis());
            stmt.setDate(3, movie.getRelease_date());
            stmt.setString(4, movie.getPoster());
            stmt.setDate(5, new Date(System.currentTimeMillis()));
            stmt.setInt(6, movie.getId());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 1) {
                return movie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete given movie from the database.
     * @param movie
     * @return bool
     */
    public boolean delete(Movie movie) {
        try {
            String query = "DELETE FROM movies WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, movie.getId());
            int result = stmt.executeUpdate();
            if (result == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * This method buils a movie object from a database Result
     * @param result
     * @return
     * @throws SQLException
     */
    private Movie buildMovieObject(ResultSet result) throws SQLException {
        Movie movie = new Movie();
        movie.setId(result.getInt("id"));
        movie.setTitle(result.getString("title"));
        movie.setSynopsis(result.getString("synopsis"));
        movie.setRelease_date(result.getDate("release_date"));
        movie.setPoster(result.getString("poster"));
        movie.setCreated_at(result.getDate("created_at"));
        movie.setUpdated_at(result.getDate("updated_at"));

        return movie;
    }
}