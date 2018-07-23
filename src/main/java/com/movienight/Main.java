package com.movienight;

import com.movienight.model.Event;
import com.movienight.model.Genre;
import com.movienight.model.Movie;
import com.movienight.model.User;
import com.movienight.model.dao.EventDaoOracle;
import com.movienight.model.dao.GenreDaoOracle;
import com.movienight.model.dao.MovieDaoOracle;
import com.movienight.model.dao.UserDaoOracle;

import java.sql.Date;
import java.sql.SQLException;
public class Main {

    private static UserDaoOracle userDao = new UserDaoOracle();
    private static MovieDaoOracle movieDao = new MovieDaoOracle();
    private static GenreDaoOracle genreDao = new GenreDaoOracle();

    public static void main(String[] args) {
        // User CRUD test
//        User user = new User(1, "Donneh");
//        UserDaoOracle userDao = new UserDaoOracle();
//        System.out.println(userDao.find(1));
//        System.out.println(userDao.save(user));
//        user.setUsername("Donny");
//        System.out.println(userDao.update(user));
//        System.out.println(userDao.find(1));

        // Genre CRUD test
        GenreDaoOracle genreDaoOracle = new GenreDaoOracle();
        Genre genre = new Genre("Family");
        System.out.println(genreDaoOracle.save(genre));

        // Movie CRUD test
        MovieDaoOracle movieDaoOracle = new MovieDaoOracle();
        Movie movie = new Movie();
        movie.setTitle("Paddington 2");
        movie.setSynopsis("Paddington, now happily settled with the Brown family and a popular member of the local community, picks up a series of odd jobs to buy the perfect present for his Aunt Lucy's 100th birthday, only for the gift to be stolen.");
        movie.setPoster("https://m.media-amazon.com/images/M/MV5BMmYwNWZlNzEtNjE4Zi00NzQ4LWI2YmUtOWZhNzZhZDYyNmVmXkEyXkFqcGdeQXVyNzYzODM3Mzg@._V1_.jpg");
        movie.setRelease_date(new Date(System.currentTimeMillis()));
        movie.addGenre(genreDaoOracle.findByName("Family"));
        System.out.println(movieDaoOracle.save(movie));
//
//

        // Event CRUD test
//        EventDaoOracle eventDaoOracle = new EventDaoOracle();
//        Event event = new Event();
//        event.setName("Filmparty");
//        event.setHost(userDao.find(1));
//        System.out.println(eventDaoOracle.save(event));
    }
}
