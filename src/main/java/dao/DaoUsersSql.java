package dao;

import domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DaoUsersSql implements Dao<User> {
    private Connection connection;

    public DaoUsersSql(Connection connection) {
        this.connection = connection;
    }
    public void add(User user) {
        String sql = "INSERT INTO tinder_users (login, password, name, surname, img_url) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getName());
            stm.setString(4, user.getSurname());
            stm.setString(5, user.getImgUrl());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        throw new IllegalStateException("Method is not supplied by this implementation");
    }

    public User getUserToShow(int activeUserId) {
        User result = null;
        String sql = "SELECT * FROM tinder_users WHERE id != ? AND id NOT IN (\n" +
                    "SELECT checked FROM tinder_checked WHERE checked = id AND id = ? \n" +
                ") LIMIT 1";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, activeUserId);
            stm.setInt(2, activeUserId);
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                String name = rSet.getString("name");
                int id = rSet.getInt("id");
                String surname = rSet.getString("surname");
                String login = rSet.getString("login");
                String password = rSet.getString("password");
                String imgUrl = rSet.getString("img_url");
                result = new User(id, login, password, name, surname, imgUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }
    public User getByLogin(User user) {
        User result = null;
        String sql = "SELECT * FROM tinder_users WHERE login = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getLogin());
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                String name = rSet.getString("name");
                String surname = rSet.getString("surname");
                int id = rSet.getInt("id");
                String login = rSet.getString("login");
                String password = rSet.getString("password");
                String imgUrl = rSet.getString("img_url");
                result = new User(id, login, password, name, surname, imgUrl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User get(int id) {
        User user = null;
        String sql = "SELECT tinder_users.name, tinder_users.surname,tinder_users.login,tinder_users.img_url FROM tinder_users WHERE id = ?";;
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                String name = rSet.getString("name");
                String surname = rSet.getString("surname");
                String login = rSet.getString("login");
                String imgUrl = rSet.getString("img_url");
                user = new User(id, login, name, surname, imgUrl);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }

    @Override
    public List<User> getAll() {
        throw new IllegalStateException("Method is not supplied by this implementation");
    }

}
