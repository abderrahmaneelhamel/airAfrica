package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Admin;
import com.example.airafrica.Entity.Traveller;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class AdminRepository {
    private final SessionFactory sessionFactory;

    public AdminRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Admin> getAllAdmins() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Admin", Admin.class).list();
        }
    }

    public Admin Authenticate(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Admin WHERE email = :email AND password = :password", Admin.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Admin getAdminById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Admin WHERE id = :id", Admin.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteAdmin(int id) {
        try (Session session = sessionFactory.openSession()) {
            Admin admin = session.createQuery("FROM Admin WHERE id = :id", Admin.class)
                    .setParameter("id", id)
                    .uniqueResult();
            if (admin != null) {
                session.delete(admin);
            }
        }
    }
    public Void UpdateAdmin(Admin admin) {
        try (Session session = sessionFactory.openSession()) {
            session.update(admin);
            return null;
        }
    }
    public void saveAdmin(Admin admin){
        try (Session session = sessionFactory.openSession()) {
            session.save(admin);
        }
    }
}
