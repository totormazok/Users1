package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;
import org.hibernate.Transaction;
import org.hibernate.Session;

import java.util.ArrayList;
import jm.task.core.jdbc.util.Util;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction tс = null;
        try (Session session = Util.getSessionFactory()) {
            User user = new User();
            tс = session.beginTransaction();
            session.createSQLQuery(String.format("CREATE TABLE IF NOT EXISTS  %s ( id INT not NULL AUTO_INCREMENT, " +
                            "name VARCHAR(40), lastName VARCHAR(40), age INT, PRIMARY KEY ( id ))",
                    user.getClass().getSimpleName())).executeUpdate();
            tс.commit();
        } catch (Exception e) {
            tс.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tс = null;
        try (Session session = Util.getSessionFactory()) {
            User user = new User();
            tс = session.beginTransaction();
            session.createSQLQuery ("drop table if exists " + user.getClass().getSimpleName()).executeUpdate();
            tс.commit();
        } catch (Exception e) {
            tс.rollback();
            e.printStackTrace();
        }
    }



    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tс = null;
        try (Session session = Util.getSessionFactory()) {
            User user = new User(name, lastName, age);
            tс = session.beginTransaction();
            session.save(user);
            tс.commit();
        } catch (Exception e) {
            tс.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tс = null;
        try (Session session = Util.getSessionFactory()) {
            tс = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            tс.commit();
        } catch (Exception e) {
            tс.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Transaction tс = null;
        try (Session session = Util.getSessionFactory()) {
            User user = new User();
            tс = session.beginTransaction();
            result.addAll(session.createQuery("FROM " + user.getClass().getSimpleName()).getResultList());
            tс.commit();
        } catch (Exception e) {
            tс.rollback();
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tс = null;
        try (Session session = Util.getSessionFactory()) {
            User user = new User();
            tс = session.beginTransaction();
            session.createSQLQuery("DELETE FROM " + user.getClass().getSimpleName()).executeUpdate();
            tс.commit();
        } catch (Exception e) {
            tс.rollback();
            e.printStackTrace();
        }
    }
}
