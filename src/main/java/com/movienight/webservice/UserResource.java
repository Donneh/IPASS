package com.movienight.webservice;

import com.movienight.model.dao.UserDaoPostgres;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    private UserDaoPostgres userDao = new UserDaoPostgres();

    @GET
    @Produces("application/json")
    public Response getUsers() {
        return Response.ok(userDao.index()).build();
    }

    @GET
    @Produces("application/json")
    @Path("/hasWatched")
    public Response hasWatched(@QueryParam("movie_id") int movie_id) {
        return Response.ok(userDao.hasWatched(movie_id)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/watched")
    public Response watched(@QueryParam("movie_id") int movie_id) {
        return Response.ok(userDao.markWatched(movie_id)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/notWatched")
    public Response notWatched(@QueryParam("movie_id") int movie_id) {
        return Response.ok(userDao.markNotWatched(movie_id)).build();
    }
}