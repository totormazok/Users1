package jm.task.core.jdbc.service;


import com.mysql.cj.jdbc.StatementImpl;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS tabletwo" +
                    "(id INT NOT NULL AUTO_INCREMENT,  firstName VARCHAR(40), lastName VARCHAR(40), age INT, PRIMARY KEY (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        String drop = "DROP TABLE IF EXISTS tabletwo";
        try (Statement statement = connection.createStatement()) {
            statement.execute(drop);
            System.out.println("таблица удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO tabletwo(firstName, lastName, age) VALUES(?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate("INSERT INTO tabletwo(firstName, lastName, age) " +
                    "VALUES('" + name + "', '" + lastName + "', '" + age + "')");
            connection.commit();
            System.out.println("Пользователь: " + name + " " + lastName +  " - возраст: " + age + " " + " лет, добавлен");
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tabletwo WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String userId = "1";
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tabletwo WHERE id = ?");
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String clean = "TRUNCATE TABLE tabletwo";
        try (Statement statement = connection.createStatement()) {
            statement.execute(clean);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
