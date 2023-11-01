package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Flight;
import com.example.airafrica.Entity.Reservation;
import com.example.airafrica.Entity.ReservationExtras;
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

            // Retrieve the Flight entity by ID
            Flight flight = session.get(Flight.class, reservation.getFlight().getId());

            // Check if there is enough capacity for the reservation
            if (flight.getCapacity() > 0) {
                // Decrease the capacity by 1
                flight.setCapacity(flight.getCapacity() - 1);

                // Save the updated Flight entity
                session.update(flight);

                // Save the reservation
                session.save(reservation);

                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
            }
        }
    }

    public void updateReservationWithBaggage(Reservation reservation, int baggageWeight) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Flight flight = session.get(Flight.class, reservation.getFlight().getId());

            // Check if there is enough capacity for the reservation
            if (flight.getCapacity() > 0) {
                // Decrease the capacity by 1
                flight.setCapacity(flight.getCapacity() - 1);

                // Update baggage weight and total cost
                reservation.setBaggageWeight(baggageWeight);

                // Update the Flight entity with the new capacity
                session.update(flight);

                // Update the Reservation entity with baggage weight and total cost
                session.update(reservation);

                session.getTransaction().commit();
            } else {
                session.getTransaction().rollback();
            }
        }
    }

    public void addReservationExtras(ReservationExtras reservationExtras) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(reservationExtras);
            session.getTransaction().commit();
        }
    }
}

