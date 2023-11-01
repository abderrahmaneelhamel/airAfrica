package com.example.airafrica.servlets;

import java.io.*;
import java.util.List;

import com.example.airafrica.Entity.Admin;
import com.example.airafrica.Entity.Airport;
import com.example.airafrica.Repository.AdminRepository;
import com.example.airafrica.Repository.AirportRepository;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.hibernate.SessionFactory;

@WebServlet(name = "HomeServlet", value = "/home")
public class HomeServlet extends HttpServlet {
    private final AirportRepository airportRepository;
    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public HomeServlet() {
        airportRepository = new AirportRepository(sessionFactory);
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Airport> airports = airportRepository.getAllAirports();
        for (Airport airport : airports){
            System.out.println(airport.toString());
        }

        request.getSession().setAttribute("airports", airports);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
