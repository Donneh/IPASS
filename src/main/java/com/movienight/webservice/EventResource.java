package com.movienight.webservice;

import com.movienight.model.Event;
import com.movienight.model.dao.EventDaoPostgres;
import com.movienight.model.dao.UserDaoPostgres;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/events")
public class EventResource {

    EventDaoPostgres eventDaoPostgres = new EventDaoPostgres();
    UserDaoPostgres userDaoPostgres = new UserDaoPostgres();

    @GET
    @Produces("application/json")
    public Response getEvents() {
        return Response.ok(eventDaoPostgres.index()).build();
    }

    @GET
    @Path("/user")
    @Produces("application/json")
    public Response getEventsByHost(@QueryParam("id") int id) {
        return Response.ok(eventDaoPostgres.findByHost(id)).build();
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
        event.setDescription(description);
        event.setHost(userDaoPostgres.find(host));
        return Response.ok(eventDaoPostgres.save(event)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/event")
    public Response getEvent(@QueryParam("id") int id) {
        return Response.ok(eventDaoPostgres.find(id)).build();
    }

    @POST
    @Produces("application/json")
    @Path("/event/addGuests")
    public Response addQuests(
            @QueryParam("event_id") int event,
            @QueryParam("guests") String guests
    ) {
        System.out.println(event);
        System.out.println(guests);
        return null;
    }

    @PUT
    @Produces("application/json")
    @Path("/event/update")
    public Response updateEvent(
            @FormParam("event_id") int event_id,
            @FormParam("name") String name,
            @FormParam("description") String description,
            @FormParam("Date") String date
    ) {
        Event event = new Event();
        event.setId(event_id);
        event.setName(name);
        event.setDescription(description);
        return Response.ok(eventDaoPostgres.update(event)).build();
    }

    @DELETE
    @Produces("application/json")
    @Path("/event/delete")
    public Response deleteEvent(@QueryParam("id") int id) {
        Event event = new Event();
        event.setId(id);
        return Response.ok(eventDaoPostgres.delete(event)).build();
    }

}
