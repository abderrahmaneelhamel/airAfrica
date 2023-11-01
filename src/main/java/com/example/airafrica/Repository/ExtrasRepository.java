package com.example.airafrica.Repository;

import com.example.airafrica.Entity.Extras;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ExtrasRepository {
    private final SessionFactory sessionFactory;

    public ExtrasRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Extras> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Extras> query = session.createQuery("FROM Extras", Extras.class);
            return query.list();
        }
    }

    public Extras findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Extras.class, id);
        }
    }

    public void save(Extras extras) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(extras);
            tx.commit();
        }
    }

    public void update(Extras extras) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(extras);
            tx.commit();
        }
    }

    public void delete(Extras extras) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(extras);
            tx.commit();
        }
    }
}
