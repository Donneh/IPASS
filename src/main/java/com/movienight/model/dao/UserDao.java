package com.movienight.model.dao;

import com.movienight.model.User;

import java.util.List;

public class UserDao extends BaseDao {

    public User save(User user) {
        User result = find(user.getId());
        begin();
        if(result == null) {
            em.persist(user);
            em.flush();
        }
        em.close();
        int id = user.getId();
        commit();

        return find(id);
    }

    public User find(int id) {
        begin();
        User user = em.find(User.class, id);
        commit();
        em.close();
        return user;
    }

    public List<User> index() {
        begin();
        List<User> results = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        commit();
        em.close();
        return results;
    }

    public User update(User user) {
        begin();
        em.merge(user);
        commit();
        em.close();
        return user;
    }
}