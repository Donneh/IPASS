package com.movienight.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BaseDao {

    private String orclcfg = "com.movienight";
    private EntityManagerFactory entityManagerFactory;
    protected EntityManager em;

    public BaseDao() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(orclcfg);
            em = entityManagerFactory.createEntityManager();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    protected void begin() {
        em.getTransaction().begin();
    }

    protected void commit() {
        em.getTransaction().commit();
    }
}
