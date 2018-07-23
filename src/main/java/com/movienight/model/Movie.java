package com.movienight.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Movie {

    private int id;
    private String title;
    private Date release_date;
    private String synopsis;
    private String poster;
    private Date created_at;
    private Date updated_at;
    private List<Genre> genres = new ArrayList<>();

    public Movie(String title, Date release_date, String synopsis, String poster) {
        this.title = title;
        this.release_date = release_date;
        this.synopsis = synopsis;
        this.poster = poster;
    }

    public Movie() {
    }

    public Movie(String title, Date release_date, String synopsis, String poster, List<Genre> genres) {
        this.title = title;
        this.release_date = release_date;
        this.synopsis = synopsis;
        this.poster = poster;
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", release_date=" + release_date +
                ", synopsis='" + synopsis + '\'' +
                ", poster='" + poster + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", genres=" + genres +
                '}';
    }
}
