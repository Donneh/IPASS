package com.movienight.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private int id;
    private String username;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "User_Movie",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "movie_id") }
    )
    private List<Movie> watched = new ArrayList<Movie>();

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
