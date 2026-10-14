package com.example.Persistence;

import com.example.Logic.Mascota;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class GenericDAOImplementation<T, ID> {
    protected Class<T> entityClass;
    protected SessionFactory sessionFactory;

    public GenericDAOImplementation(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public T findById(ID id) {
        try (Session session = getSession()) {
            return session.get(entityClass, id);
        }
    }

    public List<T> findAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM " + entityClass.getName(), entityClass).list();
        }
    }

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.update(entity);  // Use the object entity itself
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(ID id) {
        Transaction transaction = null;
        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            T entity = session.get(entityClass, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;


        }
    }
}
