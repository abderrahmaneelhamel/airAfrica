package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Admin;
import com.example.airafrica.Entity.Traveller;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class TravellerRepository {
    private final SessionFactory sessionFactory;

    public TravellerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Traveller> getAllTravellers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Traveller", Traveller.class).list();
        }
    }
    public Traveller getTravellerById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Traveller WHERE id = :id", Traveller.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }
    public Traveller Authenticate(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Traveller WHERE email = :email AND password = :password", Traveller.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void deleteTraveller(int id) {
        try (Session session = sessionFactory.openSession()) {
            Traveller traveller = session.createQuery("FROM Traveller WHERE id = :id", Traveller.class)
                    .setParameter("id", id)
                    .uniqueResult();
            if (traveller != null) {
                session.delete(traveller);
            }
        }
    }
    public void updateTraveller(Traveller traveller){
        try (Session session = sessionFactory.openSession()) {
            session.update(traveller);
        }
    }
    public Traveller saveTraveller(Traveller traveller){
        try (Session session = sessionFactory.openSession()) {
            session.save(traveller);
            return traveller;
        }
    }
}
