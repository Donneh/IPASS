package com.movienight.model.dao;

import com.movienight.model.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventDaoOracle extends OracleBaseDao {

    private UserDaoOracle userDaoOracle;

    public EventDaoOracle() {
        this.userDaoOracle = new UserDaoOracle();
    }

    /**
     * Find event with the specified id.
     * @param id
     * @return Event event
     */
    public Event find(int id) {
        try {
            String query = "SELECT * FROM events WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return buildEventObject(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Save event to the database.
     * @param event
     * @return Event event
     */
    public Event save(Event event) {
        try {
            String query =
                    "INSERT INTO events " +
                    "(name, host) VALUES (?, ?)";
            String generatedColumns[] = { "id" };
            PreparedStatement stmt = getConnection().prepareStatement(query, generatedColumns);
            stmt.setString(1, event.getName());
            stmt.setInt(2, event.getHost().getId());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return find(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve all events from the database.
     * @return List
     */
    public List<Event> index() {
        try {
            String query = "SELECT * FROM events";
            Statement stmt = getConnection().createStatement();
            ResultSet result = stmt.executeQuery(query);
            List<Event> events = new ArrayList<>();
            if(result.next()) {
                events.add(buildEventObject(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update existing event in the database.
     * @param event
     * @return Event | null
     */
    public Event update(Event event) {
        try {
            String query =
                    "UPDATE events SET " +
                    "name = ? WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, event.getName());
            stmt.setInt(2, event.getId());

            ResultSet result = stmt.executeQuery();
            if(result.next()) {
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete event from the database.
     * @param event
     * @return bool
     */
    public boolean delete(Event event) {
        try {
            String query = "DELETE FROM events WHERE id = ?";
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, event.getId());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method builds an Event object from the database result.
     * @param rs
     * @returnE vent
     * @throws SQLException
     */
    private Event buildEventObject(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setName(rs.getString("name"));
        event.setHost(userDaoOracle.find(rs.getInt("host")));
        return event;
    }
}
