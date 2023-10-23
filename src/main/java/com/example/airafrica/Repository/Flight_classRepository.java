package com.example.airafrica.Repository;

import com.example.airafrica.Entity.FlightClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Flight_classRepository {

    private final SessionFactory sessionFactory;

    public Flight_classRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public FlightClass getFlightClass(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM FlightClass WHERE id = :id", FlightClass.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }
}
