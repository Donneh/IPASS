package com.movienight.webservice;

import com.movienight.model.Event;
import com.movienight.model.dao.EventDaoOracle;
import com.movienight.model.dao.UserDaoOracle;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/events")
public class EventResource {

    EventDaoOracle eventDaoOracle = new EventDaoOracle();
    UserDaoOracle userDaoOracle = new UserDaoOracle();

    @GET
    @Produces("application/json")
    public Response getEvents() {
        return Response.ok(eventDaoOracle.index()).build();
    }

    @GET
    @Path("/user")
    @Produces("application/json")
    public Response getEventsByUser(@QueryParam("id") int id) {
        return null;
    }

    @POST
    @Produces("application/json")
    public Response createEvent(
            @FormParam("name") String name,
            @FormParam("description") String description,
            @FormParam("Date") String date,
            @FormParam("host") int host
    ) {
        Event event = new Event();
        event.setName(name);
        event.setHost(userDaoOracle.find(host));
        return Response.ok(eventDaoOracle.save(event)).build();
    }

}
