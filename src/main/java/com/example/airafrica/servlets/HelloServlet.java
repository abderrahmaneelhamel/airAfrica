package com.example.airafrica.servlets;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/❤")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "hello mr ";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        String name = request.getParameter("name");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>"+message+ name + "!</h1>");
        out.println("</body></html>");
    }


    public void destroy() {
    }
}
