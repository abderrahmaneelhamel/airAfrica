package com.example.airafrica.Repository;

import com.example.airafrica.Entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }
}
