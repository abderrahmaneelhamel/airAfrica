package com.example.airafrica.servlets;
import com.example.airafrica.Entity.User;
import com.example.airafrica.Repository.UserRepository;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {
    private final UserRepository UserRepository;
    private static final SessionFactory sessionFactory = Persistence.createEntityManagerFactory("PersistenceUnit").unwrap(SessionFactory.class);

    public UserListServlet() {
        UserRepository = new UserRepository(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve all users
        List<User> users = UserRepository.getAllUsers();

        // Print the users to the console
        for (User user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("Name: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Password: " + user.getPassword());
            System.out.println();
        }

        // Respond with an HTML page or other desired output
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>User List</h1>");
        for (User user : users) {
            response.getWriter().println("<p>User ID: " + user.getId() + "</p>");
            response.getWriter().println("<p>Name: " + user.getUsername() + "</p>");
            response.getWriter().println("<p>Email: " + user.getEmail() + "</p>");
            response.getWriter().println("<p>Password: " + user.getPassword() + "</p>");
            response.getWriter().println("<hr/>");
        }
        response.getWriter().println("</body></html>");
    }
}
