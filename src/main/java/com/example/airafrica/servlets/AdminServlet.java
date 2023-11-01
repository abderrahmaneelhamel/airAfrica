package com.example.airafrica.servlets;

import com.example.airafrica.Entity.Airline;
import com.example.airafrica.Entity.Airport;
import com.example.airafrica.Entity.Flight;
import com.example.airafrica.Repository.AirlineRepository;
import com.example.airafrica.Repository.AirportRepository;
import com.example.airafrica.Repository.FlightRepository;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@WebServlet("/dashboard")
public class AdminServlet extends HttpServlet {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;

    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public AdminServlet() {
        flightRepository = new FlightRepository(sessionFactory);
        airlineRepository = new AirlineRepository(sessionFactory);
        airportRepository = new AirportRepository(sessionFactory);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("admin") == null){
            response.sendRedirect("/login");
        }else {
            List<Flight> flights = flightRepository.getAllFlights();
            List<Airport> airports = airportRepository.getAllAirports();
            List<Airline> airlines = airlineRepository.getAllAirlines();
            request.setAttribute("flights", flights);
            request.setAttribute("airports", airports);
            request.setAttribute("airlines", airlines);
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            FlightCreation(request, response);
        } else if ("update".equals(action)) {
            FlightUpdate(request, response);
        } else if ("delete".equals(action)) {
            FlightDeletion(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/dashboard");
        }
    }

    private void FlightCreation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int departureAirport_id = Integer.parseInt(request.getParameter("departureAirport"));
        int arrivalAirport_id = Integer.parseInt(request.getParameter("arrivalAirport"));
        String DepartureDateStr = request.getParameter("departureDate");
        String ArrivalDateStr = request.getParameter("arrivalDate");
        int Capacity = Integer.parseInt(request.getParameter("capacity"));
        int AirLine_id = Integer.parseInt(request.getParameter("airline_id"));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date DepartureDate = dateFormat.parse(DepartureDateStr);
            Date ArrivalDate = dateFormat.parse(ArrivalDateStr);

            Airline airline = airlineRepository.getAirlineById(AirLine_id);
            Airport departureAirport = airportRepository.getAirportById(departureAirport_id);
            Airport arrivalAirport = airportRepository.getAirportById(arrivalAirport_id);

            Flight newFlight = new Flight(departureAirport,arrivalAirport,DepartureDate,ArrivalDate,Capacity,airline);

            flightRepository.saveFlight(newFlight);
        }catch (ParseException e){
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private void FlightUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int flightId = Integer.parseInt(request.getParameter("flightId"));
        Flight flightToUpdate = flightRepository.getFlight(flightId);
        if (flightToUpdate != null) {
            String updatedDepartureDateStr = request.getParameter("departureDate");
            String updatedArrivalDateStr = request.getParameter("arrivalDate");
            int capacity = Integer.parseInt(request.getParameter("capacity"));

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Date updatedDepartureDate = dateFormat.parse(updatedDepartureDateStr);
                Date updatedArrivalDate = dateFormat.parse(updatedArrivalDateStr);

                if(capacity > 0){
                    flightToUpdate.setCapacity(capacity);
                }
                flightToUpdate.setDepartureDate(updatedDepartureDate);
                flightToUpdate.setArrivalDate(updatedArrivalDate);

                flightRepository.updateFlight(flightToUpdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }

    private void FlightDeletion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int flightId = Integer.parseInt(request.getParameter("flightId"));

            flightRepository.DeleteFlight(flightId);

        response.sendRedirect(request.getContextPath() + "/dashboard");
    }
}