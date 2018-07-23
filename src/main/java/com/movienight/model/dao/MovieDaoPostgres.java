package com.movienight.model.dao;

import com.movienight.model.Genre;
import com.movienight.model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDaoPostgres extends PostgresBaseDao {

    private GenreDaoPostgres genreDaoPostgres = new GenreDaoPostgres();

    /**
     * Save a movie tot he database.
     * @param movie
     * @return Movie | null
     */
    public Movie save(Movie movie) {
        try(Connection conn = getConnection()) {
            String query =
                    "INSERT INTO movies " +
                            "(title, synopsis, poster, release_date, created_at) " +
                            "VALUES (?, ?, ?, ?, ?)";
            String generatedColumns[] = { "id" };
            PreparedStatement stmt = conn.prepareStatement(query, generatedColumns);
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

    private boolean addGenresToMovie(Movie movie) {
        try(Connection conn = getConnection()) {
            for (Genre genre : movie.getGenres()) {
                String genreQuery = "INSERT INTO MOVIE_GENRE (MOVIE_ID, GENRE_ID) VALUES (?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(genreQuery);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Find a movie with the given id.
     * @param id
     * @return Movie | null
     */
    public Movie find(int id) {
        try(Connection conn = getConnection()) {
            String query = "SELECT id, title, synopsis, poster, release_date, created_at, updated_at FROM movies WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Movie movie = buildMovieObject(result);
                movie.setGenres(genreDaoPostgres.findByMovie(id));
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
        try(Connection conn = getConnection()) {
            List<Movie> movies = new ArrayList<>();
            String query = "SELECT * FROM movies";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);
            while (result.next()) {
                Movie movie = buildMovieObject(result);
                movie.setGenres(genreDaoPostgres.findByMovie(movie.getId()));
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
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM movies WHERE LOWER(title) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, '%' + searchQuery.toLowerCase() + '%');
            ResultSet result = stmt.executeQuery();
            List<Movie> movies = new ArrayList<>();
            while (result.next()) {
                Movie movie = buildMovieObject(result);
                movie.setGenres(genreDaoPostgres.findByMovie(movie.getId()));
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
        try(Connection conn = getConnection()) {
            String query =
                    "UPDATE movies SET " +
                    "title = ?, synopsis = ?, release_date = ?, poster = ?, updated_at = ? " +
                    "WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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
        try(Connection conn = getConnection()) {
            String query = "DELETE FROM movies WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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