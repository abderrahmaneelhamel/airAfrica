package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Reservation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class ReservationRepository {
    private final SessionFactory sessionFactory;

    public ReservationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Reservation> getAllReservations() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reservation", Reservation.class).list();
        }
    }
    public Reservation getReservationById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Reservation WHERE id = :id", Reservation.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public void deleteReservation(Reservation reservation) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(reservation);
            session.getTransaction().commit();
        }
    }
    public void updateReservation(Reservation reservation) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(reservation);
            session.getTransaction().commit();
        }
    }

    public void makeReservation(Reservation reservation) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(reservation);
            session.getTransaction().commit();
        }
    }

}

