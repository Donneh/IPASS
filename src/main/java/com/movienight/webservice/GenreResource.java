package com.movienight.webservice;

import com.movienight.model.dao.GenreDaoOracle;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/genres")
public class GenreResource {

    private GenreDaoOracle genreDaoOracle = new GenreDaoOracle();

    @GET
    @Produces("application/json")
    public Response getGenres() {
        return Response.ok(genreDaoOracle.index()).build();
    }
}
