package com.movienight.webservice;

import com.movienight.model.dao.UserDaoOracle;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    private UserDaoOracle userDao = new UserDaoOracle();

    @GET
    @Produces("application/json")
    public Response getUsers() {
        return Response.ok(userDao.index()).build();
    }
}