package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Airport;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AirportRepository {
    private final SessionFactory sessionFactory;

    public AirportRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Airport> getAllAirports() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Airport", Airport.class).list();
        }
    }
    public Airport getAirportById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Airport WHERE id = :id", Airport.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public void deleteAirport(Airport airport) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(airport);
            session.getTransaction().commit();
        }
    }
    public void updateAirport(Airport airport) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(airport);
            session.getTransaction().commit();
        }
    }

    public void saveAirport(Airport airport) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(airport);
            session.getTransaction().commit();
        }
    }

}

