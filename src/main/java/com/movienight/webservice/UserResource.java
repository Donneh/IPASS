package com.movienight.webservice;

import com.movienight.model.dao.UserDao;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    private UserDao userDao = new UserDao();

    @GET
    @Produces("application/json")
    public Response getUsers() {
        return Response.ok(userDao.index()).build();
    }
}
