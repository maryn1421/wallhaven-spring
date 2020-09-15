package com.codecool.wallhaven.Dao.implementation;

import com.codecool.wallhaven.Dao.UserDao;
import com.codecool.wallhaven.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDaoJDBC implements UserDao {
    private DataSource dataSource;

    public UserDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    @Override
    public void addUser(User user) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO users (friends, email, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Array array = conn.createArrayOf("Integer", new Object[]{});
            statement.setArray(1, array);
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getName());
            statement.setString(4, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUSer() {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void updateUser(User uSer) {

    }

    @Override
    public List<User> getAllUser() {
        return null;
    }
}
