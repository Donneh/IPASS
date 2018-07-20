package com.movienight;

import com.movienight.model.Event;
import com.movienight.model.Genre;
import com.movienight.model.Movie;
import com.movienight.model.User;
import com.movienight.model.dao.EventDao;
import com.movienight.model.dao.GenreDao;
import com.movienight.model.dao.MovieDao;
import com.movienight.model.dao.UserDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static UserDao userDao = new UserDao();
    private static MovieDao movieDao = new MovieDao();
    private static GenreDao genreDao = new GenreDao();

    public static void main(String[] args) {
        addMovies();

        User user = new User();
        user.setId(1);
        user.setUsername("Donneh");

        User donneh = userDao.save(user);
        System.out.println(donneh);

        Movie movie = movieDao.find(1);

        donneh.getWatched().add(movie);
        System.out.println(userDao.update(donneh));

        System.out.println(movieDao.search("Paddington"));


        Event event = new Event();
        event.setName("Party");
        event.setHost(userDao.find(1));

        EventDao eventDao = new EventDao();
        System.out.println(eventDao.save(event));

    }

    public static void addMovies() {
        MovieDao md = new MovieDao();

        List<Genre> genres = new ArrayList<>();
        Genre adventure = new Genre("Adventure");
        genres.add(adventure);
        Genre comedy = new Genre("Comedy");
        genres.add(comedy);
        Genre family = new Genre("Family");
        genres.add(family);

        Movie paddington = new Movie(
                "Paddington",
                new Date(2015, 2, 11),
                "A young Peruvian bear travels to London in search of a home. Finding himself lost and alone at Paddington Station, he meets the kindly Brown family, who offer him a temporary haven.",
                "https://m.media-amazon.com/images/M/MV5BMTAxOTMwOTkwNDZeQTJeQWpwZ15BbWU4MDEyMTI1NjMx._V1_SY1000_CR0,0,675,1000_AL_.jpg",
                genres
        );
        System.out.println(md.save(paddington));
        Movie paddington2 = new Movie(
                "Paddington 2",
                new Date(2017, 12, 6),
                "Paddington, now happily settled with the Brown family and a popular member of the local community, picks up a series of odd jobs to buy the perfect present for his Aunt Lucy's 100th birthday, only for the gift to be stolen.",
                "https://m.media-amazon.com/images/M/MV5BMmYwNWZlNzEtNjE4Zi00NzQ4LWI2YmUtOWZhNzZhZDYyNmVmXkEyXkFqcGdeQXVyNzYzODM3Mzg@._V1_.jpg",
                genres
        );
        System.out.println(md.save(paddington2));

        genres =  new ArrayList<Genre>();
        genres.add(new Genre("Horror"));
        Movie movie = new Movie(
                "A Quiet Place",
                new Date(2018, 01, 01),
                "In a post-apocalyptic world, a family is forced to live in silence while hiding from monsters with ultra-sensitive hearing.",
                "https://m.media-amazon.com/images/M/MV5BMjI0MDMzNTQ0M15BMl5BanBnXkFtZTgwMTM5NzM3NDM@._V1_SY1000_CR0,0,674,1000_AL_.jpg",
                genres
        );
        md.save(movie);
    }
}
