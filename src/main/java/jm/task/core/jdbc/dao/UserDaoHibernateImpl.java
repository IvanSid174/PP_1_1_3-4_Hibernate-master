package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory factory =  Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        try {
            session.createNativeQuery("CREATE TABLE IF NOT EXISTS Users (id mediumint not null auto_increment," +
                    "name VARCHAR(20), lastname VARCHAR(20), age tinyint, PRIMARY KEY (id))").executeUpdate();
            System.out.println("Таблица создана");

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    @Override
    public void dropUsersTable() {
        Session session = factory.getCurrentSession();;
        session.beginTransaction();
        try {
            session.createNativeQuery(" DROP TABLE IF EXISTS Users").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        try {
            User user = new User(name, lastName, age);
            session.save(user);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            session.remove(user);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();

        } finally {
            session.close();

        }

    }

    @Override
    public List<User> getAllUsers() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root);

        List<User> results = session.createQuery(cr).getResultList();

        try{
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }


    @Override
    public void cleanUsersTable() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        try {
            session.createNativeQuery("TRUNCATE TABLE Users")
                    .executeUpdate();
            System.out.println("Таблица очищена");

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();

        } finally {
            session.close();
        }
    }
}
