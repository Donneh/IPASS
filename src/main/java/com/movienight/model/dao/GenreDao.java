package com.movienight.model.dao;

import com.movienight.model.Genre;

public class GenreDao extends BaseDao {

    public Genre save(Genre genre) {
        begin();
        em.persist(genre);
        em.flush();
        int id = genre.getId();
        commit();
        em.close();
        return find(id);
    }

    public Genre find(int id) {
        begin();
        Genre genre = em.find(Genre.class, id);
        commit();
        em.close();
        return genre;
    }
}