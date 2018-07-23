package com.movienight.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String username;
    private List<Movie> watched = new ArrayList<Movie>();

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Movie> getWatched() {
        return watched;
    }

    public void setWatched(List<Movie> watched) {
        this.watched = watched;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", watched=" + watched +
                '}';
    }
}
