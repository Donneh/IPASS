package com.movienight.model.dao;

import com.movienight.model.Event;
import com.movienight.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDaoPostgres extends PostgresBaseDao {

    private UserDaoPostgres userDaoPostgres;

    public EventDaoPostgres() {
        this.userDaoPostgres = new UserDaoPostgres();
    }

    /**
     * Find event with the specified id.
     * @param id
     * @return Event event
     */
    public Event find(int id) {
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM events WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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
        try(Connection conn = getConnection()) {
            String query =
                    "INSERT INTO events " +
                    "(name, description, host) VALUES (?, ?, ?)";
            String generatedColumns[] = { "id" };
            PreparedStatement stmt = conn.prepareStatement(query, generatedColumns);
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setInt(3, event.getHost().getId());

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
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM events";
            Statement stmt = conn.createStatement();
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
        try(Connection conn = getConnection()) {
            String query =
                    "UPDATE events SET " +
                    "name = ?, description = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDescription());
            stmt.setInt(3, event.getId());

            int affectedRows = stmt.executeUpdate();
            if(affectedRows > 0) {
                return find(event.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieve all events where the given user is the host.
     * @param user_id
     * @return
     */
    public List<Event> findByHost(int user_id) {
        List<Event> events = new ArrayList<>();
        try(Connection conn = getConnection()) {
            String query = "SELECT * FROM events WHERE host = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, user_id);
            ResultSet result = stmt.executeQuery();
            while(result.next()) {
                Event event = buildEventObject(result);
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Delete event from the database.
     * @param event
     * @return bool
     */
    public boolean delete(Event event) {
        try(Connection conn = getConnection()) {
            String query = "DELETE FROM events WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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
     * Finds all the guests that are invited to the event.
     * @param event_id
     * @return
     */
    public List<User> findGuests(int event_id) {
        List<User> users = new ArrayList<>();
        try(Connection conn = getConnection()) {
            String query = "SELECT user_id FROM event_user WHERE event_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, event_id);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                users.add(userDaoPostgres.find(result.getInt("user_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
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
        event.setDescription(rs.getString("description"));
        event.setHost(userDaoPostgres.find(rs.getInt("host")));
        return event;
    }
}
