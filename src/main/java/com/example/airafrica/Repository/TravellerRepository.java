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
    public void updateLoyaltyPoints(Traveller traveller, double totalCost) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Check if the total cost is greater than or equal to 100 Euro
            if (totalCost >= 100.0) {
                // Calculate the loyalty points earned
                int loyaltyPointsEarned = (int) (totalCost / 100) * 50;

                // Update the traveler's loyalty points
                traveller.setLoyaltyPoints(traveller.getLoyaltyPoints() + loyaltyPointsEarned);

                // Save the updated traveler entity
                session.update(traveller);

                session.getTransaction().commit();
            } else {
                // If the total cost is less than 100 Euro, no loyalty points are earned
                session.getTransaction().rollback();
            }
        }
    }

}
