package com.movienight;

import com.movienight.model.Genre;
import com.movienight.model.Movie;
import com.movienight.model.dao.GenreDaoPostgres;
import com.movienight.model.dao.MovieDaoPostgres;
import com.movienight.model.dao.UserDaoPostgres;

import java.sql.Date;

public class Main {

    private static UserDaoPostgres userDao = new UserDaoPostgres();
    private static MovieDaoPostgres movieDao = new MovieDaoPostgres();
    private static GenreDaoPostgres genreDao = new GenreDaoPostgres();

    public static void main(String[] args) {
        // User CRUD test
//        User user = new User(1, "Donneh");
//        UserDaoPostgres userDao = new UserDaoPostgres();
//        System.out.println(userDao.find(1));
//        System.out.println(userDao.save(user));
//        user.setUsername("Donny");
//        System.out.println(userDao.update(user));
//        System.out.println(userDao.find(1));

        // Genre CRUD test
        GenreDaoPostgres genreDaoPostgres = new GenreDaoPostgres();
        Genre genre = new Genre("Family");
        System.out.println(genreDaoPostgres.save(genre));

        // Movie CRUD test
        MovieDaoPostgres movieDaoPostgres = new MovieDaoPostgres();
        Movie movie = new Movie();
        movie.setTitle("Paddington 2");
        movie.setSynopsis("Paddington, now happily settled with the Brown family and a popular member of the local community, picks up a series of odd jobs to buy the perfect present for his Aunt Lucy's 100th birthday, only for the gift to be stolen.");
        movie.setPoster("https://m.media-amazon.com/images/M/MV5BMmYwNWZlNzEtNjE4Zi00NzQ4LWI2YmUtOWZhNzZhZDYyNmVmXkEyXkFqcGdeQXVyNzYzODM3Mzg@._V1_.jpg");
        movie.setRelease_date(new Date(System.currentTimeMillis()));
        movie.addGenre(genreDaoPostgres.findByName("Family"));
        System.out.println(movieDaoPostgres.save(movie));
//
//

        // Event CRUD test
//        EventDaoPostgres eventDaoOracle = new EventDaoPostgres();
//        Event event = new Event();
//        event.setName("Filmparty");
//        event.setHost(userDao.find(1));
//        System.out.println(eventDaoOracle.save(event));
    }
}
