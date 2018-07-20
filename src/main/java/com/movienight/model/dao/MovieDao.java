package com.movienight.model.dao;

import com.movienight.model.Movie;

import java.util.List;

public class MovieDao extends BaseDao {

    public Movie save(Movie movie) {
        begin();
        em.persist(movie);
        em.flush();
        int id = movie.getId();
        commit();
        em.close();
        return find(id);
    }

    public Movie find(int id) {
        begin();
        Movie movie = em.find(Movie.class, id);
        commit();
        em.close();
        return movie;
    }

    public List<Movie> index() {
        begin();
        List<Movie> results = em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        commit();
        em.close();
        return results;
    }

    public List<Movie> search(String searchQuery) {
        begin();
        List<Movie> results = em.createQuery("SELECT m FROM Movie m WHERE m.title LIKE CONCAT('%', :search, '%')")
                .setParameter("search", searchQuery)
                .setMaxResults(10)
                .getResultList();
        commit();
        em.close();
        return results;
    }

    public Movie update(Movie movie) {
        begin();
        em.merge(movie);
        commit();
        em.close();
        return movie;
    }
}
