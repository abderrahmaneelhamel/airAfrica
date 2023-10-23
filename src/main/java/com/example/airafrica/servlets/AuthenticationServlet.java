package com.example.airafrica.servlets;

import java.io.IOException;

import com.example.airafrica.Entity.Admin;
import com.example.airafrica.Entity.Traveller;
import com.example.airafrica.Repository.AdminRepository;
import com.example.airafrica.Repository.TravellerRepository;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;

@WebServlet("/login")
public class AuthenticationServlet extends HttpServlet {

    private final AdminRepository adminRepository;
    private final TravellerRepository travellerRepository;
    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public AuthenticationServlet() {
        adminRepository = new AdminRepository(sessionFactory);
        travellerRepository = new TravellerRepository(sessionFactory);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the login.jsp page
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String choice = request.getParameter("choice");

        if (!choice.equals("login")){
            request.getSession().removeAttribute("Traveller");
            response.sendRedirect(request.getContextPath() + "/login");
        }else {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            Admin admin = adminRepository.Authenticate(email, password);
            if (admin != null) {
                request.getSession().setAttribute("Admin", admin);
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                Traveller traveller = travellerRepository.Authenticate(email, password);
                if (traveller != null) {
                    request.getSession().setAttribute("Traveller", traveller);
                    response.sendRedirect(request.getContextPath() + "/home");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        }
    }
}
