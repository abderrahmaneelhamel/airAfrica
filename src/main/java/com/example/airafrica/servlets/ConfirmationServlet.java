package com.example.airafrica.servlets;

import com.example.airafrica.Entity.Flight;
import com.example.airafrica.Entity.FlightClass;
import com.example.airafrica.Entity.Reservation;
import com.example.airafrica.Entity.Traveller;
import com.example.airafrica.Repository.FlightRepository;
import com.example.airafrica.Repository.Flight_classRepository;
import com.example.airafrica.Repository.ReservationRepository;
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

    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public ConfirmationServlet() {
        flightRepository = new FlightRepository(sessionFactory);
        reservationRepository = new ReservationRepository(sessionFactory);
        flight_classRepository = new Flight_classRepository(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String Flight_id = request.getParameter("flight_id");
        Flight flight = flightRepository.getFlight(Integer.parseInt(Flight_id));
        String flight_class = request.getParameter("flight_class"); // 1 : Business Class / 2 : Economy Class / 3 : First Class
        FlightClass flightClass = flight_classRepository.getFlightClass(Integer.parseInt(flight_class));
        Traveller traveller = (Traveller) request.getSession().getAttribute("Traveller");
        Date reservationDate = new Date();
        Reservation reservation = new Reservation(traveller,flight,reservationDate,flightClass);
        reservationRepository.makeReservation(reservation);
        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("/confirm_booking.jsp").forward(request, response);
    }
}