package com.movienight.model.dao;

import com.movienight.model.Event;

public class EventDao extends BaseDao {

    /**
     * Find event with the specified id.
     * @param id
     * @return Event event
     */
    public Event find(int id) {
        begin();
        Event event = em.find(Event.class, id);
        commit();
        em.close();
        return event;
    }

    /**
     * Save event to the database.
     * @param event
     * @return Event event
     */
    public Event save(Event event) {
        begin();
        em.persist(event);
        em.flush();
        commit();
        em.close();
        int id = event.getId();
        return find(id);
    }
}
