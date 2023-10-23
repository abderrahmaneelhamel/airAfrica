package com.example.airafrica.servlets;
import com.example.airafrica.Entity.Traveller;
import com.example.airafrica.Repository.TravellerRepository;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final TravellerRepository travellerRepository;
    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public RegisterServlet() {
        travellerRepository = new TravellerRepository(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Traveller traveller = travellerRepository.saveTraveller(new Traveller(req.getParameter("username"), req.getParameter("email"), req.getParameter("password")));
        req.getSession().setAttribute("Traveller", traveller);
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
