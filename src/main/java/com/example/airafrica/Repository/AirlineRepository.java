package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Airline;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class AirlineRepository {
    private final SessionFactory sessionFactory;

    public AirlineRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Airline> getAllAirlines() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Airline", Airline.class).list();
        }
    }
    public Airline getAirlineById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Airline WHERE id = :id", Airline.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public void deleteAirline(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Airline airline = session.createQuery("FROM Airline WHERE id = :id", Airline.class)
                    .setParameter("id", id)
                    .uniqueResult();
            session.delete(airline);
            session.getTransaction().commit();
        }
    }

    public void updateAirline(Airline airline) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(airline);
            session.getTransaction().commit();
        }
    }

    public void saveAirline(Airline airline) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(airline);
            session.getTransaction().commit();
        }
    }

}
