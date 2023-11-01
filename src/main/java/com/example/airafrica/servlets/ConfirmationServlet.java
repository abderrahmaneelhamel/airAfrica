package com.example.airafrica.servlets;

import com.example.airafrica.Entity.*;
import com.example.airafrica.Repository.*;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.Date;

@WebServlet("/confirmation")
public class ConfirmationServlet extends HttpServlet {
    private final FlightRepository flightRepository;
    private final Flight_classRepository flight_classRepository;
    private final ReservationRepository reservationRepository;
    private final TravellerRepository travellerRepository;
    private final ExtrasRepository extrasRepository;


    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public ConfirmationServlet() {
        flightRepository = new FlightRepository(sessionFactory);
        reservationRepository = new ReservationRepository(sessionFactory);
        flight_classRepository = new Flight_classRepository(sessionFactory);
        travellerRepository = new TravellerRepository(sessionFactory);
        extrasRepository = new ExtrasRepository(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Flight flight = flightRepository.getFlight(Integer.parseInt(request.getParameter("flight_id")));
        FlightClass flightClass = flight_classRepository.getFlightClass(Integer.parseInt(request.getParameter("flight_class")));
        Traveller traveller = (Traveller) request.getSession().getAttribute("Traveller");
        Date reservationDate = new Date();
        int baggageWeight = Integer.parseInt(request.getParameter("baggage_weight"));

        String[] selectedExtras = request.getParameterValues("selectedExtras");
        double extrasCost = calculateExtrasCost(selectedExtras);

        double totalCost = calculateTotalCost(flightClass, baggageWeight) + extrasCost;

        int loyaltyPoints = (totalCost > 100) ? (int) (totalCost / 2) : 0;
        traveller.setLoyaltyPoints(loyaltyPoints);

        Reservation reservation = new Reservation(traveller, flight, reservationDate, flightClass);
        reservation.setBaggageWeight(baggageWeight);
        reservation.setTotalCost(totalCost);
        travellerRepository.updateLoyaltyPoints(traveller, totalCost);

        // Check if the reservation is within 24 hours of the flight
        boolean isCancellable = checkCancellationEligibility(reservationDate, flight.getDepartureDate());

        if (isCancellable) {
            reservation.setCancellationStatus(true);
        }

        reservationRepository.makeReservation(reservation);

        // Create and associate ReservationExtras with the reservation
        for (String extraId : selectedExtras) {
            int extraIdInt = Integer.parseInt(extraId);
            Extras extra = extrasRepository.findById(extraIdInt);
            ReservationExtras reservationExtras = new ReservationExtras(reservation, extra);
            reservationRepository.addReservationExtras(reservationExtras);
        }

        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("/confirm_booking.jsp").forward(request, response);
    }

    private double calculateTotalCost(FlightClass flightClass, int baggageWeight) {
        double baseCost = flightClass.getBasePrice();
        double baggageCost = calculateBaggageCost(baggageWeight);
        return baseCost + baggageCost;
    }

    private double calculateBaggageCost(int baggageWeight) {
        double baseBaggageCost = 25.0;
        double additionalBaggageCost = 18.0;
        if (baggageWeight <= 5) {
            return baseBaggageCost;
        } else {
            return baseBaggageCost + (baggageWeight - 5) * additionalBaggageCost;
        }
    }

    private boolean checkCancellationEligibility(Date reservationDate, Date departureDate) {
        // Calculate the time difference in hours
        long timeDifferenceMillis = departureDate.getTime() - reservationDate.getTime();
        long timeDifferenceHours = timeDifferenceMillis / (60 * 60 * 1000);
        return timeDifferenceHours >= 24;
    }
    private double calculateExtrasCost(String[] selectedExtras) {
        double extrasCost = 0.0;

        if (selectedExtras != null) {
            for (String selectedExtra : selectedExtras) {
                int extraId = Integer.parseInt(selectedExtra);
                Extras extra = extrasRepository.findById(extraId);
                extrasCost += extra.getPrice();
            }
        }

        return extrasCost;
    }

}