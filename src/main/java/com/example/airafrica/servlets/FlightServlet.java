package com.example.airafrica.servlets;
import com.example.airafrica.Entity.Airport;
import com.example.airafrica.Entity.Flight;
import com.example.airafrica.Repository.AirportRepository;
import com.example.airafrica.Repository.FlightRepository;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/flights")
public class FlightServlet extends HttpServlet {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public FlightServlet() {
        flightRepository = new FlightRepository(sessionFactory);
        airportRepository = new AirportRepository(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if(id != null){
            Flight flight = flightRepository.getFlight(Integer.parseInt(id));
            if(flight != null){
                request.setAttribute("flight", flight);
                request.getRequestDispatcher("/flight.jsp").forward(request, response);
            }else{
                response.sendRedirect(request.getContextPath() + "/home");
            }
        }else{
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String departureAirportIdStr = request.getParameter("departureAirportId");
        String arrivalAirportIdStr = request.getParameter("arrivalAirportId");


        Flight criteriaFlight = new Flight();
        if (departureAirportIdStr != null && !departureAirportIdStr.isEmpty()) {
            int departureAirportId = Integer.parseInt(departureAirportIdStr);
            Airport departureAirport = airportRepository.getAirportById(departureAirportId);
            criteriaFlight.setDepartureAirport(departureAirport);
        }
        if (arrivalAirportIdStr != null && !arrivalAirportIdStr.isEmpty()) {
            int arrivalAirportId = Integer.parseInt(arrivalAirportIdStr);
            Airport arrivalAirport = airportRepository.getAirportById(arrivalAirportId);
            criteriaFlight.setArrivalAirport(arrivalAirport);
        }

        List<Flight> flights = flightRepository.searchFlights(criteriaFlight);

        List<Airport> airports = airportRepository.getAllAirports();
        request.setAttribute("airports", airports);
        request.setAttribute("flights", flights);
        request.setAttribute("options_selected", criteriaFlight);

        // Forward to the flights.jsp page to display the search results
        request.getRequestDispatcher("/flights.jsp").forward(request, response);
    }
}
