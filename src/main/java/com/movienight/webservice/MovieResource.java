package com.movienight.webservice;

import com.movienight.model.dao.MovieDaoOracle;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/movies")
public class MovieResource {

    private MovieDaoOracle movieDao = new MovieDaoOracle();

    @GET
    @Produces("application/json")
    public Response getMovies() {
        return Response.ok(movieDao.index()).build();
    }

    @GET
    @Path("movie")
    @Produces("application/json")
    public Response find(@QueryParam("id") String id) {
        return Response.ok(movieDao.find(Integer.parseInt(id))).build();
    }

    @GET
    @Path("/search/")
    @Produces("application/json")
    public Response searchMovie(@QueryParam("search") String search) {
        return Response.ok(movieDao.search(search)).build();
    }
}
