package com.movienight.webservice;

import com.movienight.model.dao.MovieDao;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/movies")
public class MovieResource {

    private MovieDao movieDao = new MovieDao();

    @GET
    @Produces("application/json")
    public Response getMovies() {
        return Response.ok(movieDao.index()).build();
    }
}
