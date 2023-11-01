package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Flight;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public class FlightRepository {
    private final SessionFactory sessionFactory;

    public FlightRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Flight> getAllFlights() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Flight", Flight.class).list();
        }
    }

    public Flight getFlight(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Flight WHERE id = :id", Flight.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    public List<Flight> searchFlights(Flight criteriaFlight) {
        try (Session session = sessionFactory.openSession()) {
            // Check if departureDate is null and set it to today's date
            if (criteriaFlight.getDepartureDate() == null) {
                java.sql.Date departureDate = java.sql.Date.valueOf(LocalDate.now());
                criteriaFlight.setDepartureDate(departureDate);
            }

            return session.createQuery("FROM Flight " +
                            "WHERE departureAirport = :departureAirport " +
                            "AND arrivalAirport = :arrivalAirport " +
                            "AND departureDate >= :departureDate " +
                            "AND capacity <= 30", Flight.class)
                    .setParameter("departureAirport", criteriaFlight.getDepartureAirport())
                    .setParameter("arrivalAirport", criteriaFlight.getArrivalAirport())
                    .setParameter("departureDate", criteriaFlight.getDepartureDate())
                    .list();
        }
    }

    public void saveFlight(Flight flight) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(flight);
            session.getTransaction().commit();
        }
    }
    public void updateFlight(Flight flight){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(flight);
            session.getTransaction().commit();
        }
    }

    public void DeleteFlight(int flightId) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Flight flight = session.get(Flight.class, flightId);
            if (flight != null) {
                session.delete(flight);
            }

            session.getTransaction().commit();
        }
    }


}
