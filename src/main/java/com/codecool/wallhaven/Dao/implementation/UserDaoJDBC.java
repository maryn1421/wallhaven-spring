package com.codecool.wallhaven.Dao.implementation;

import com.codecool.wallhaven.Dao.UserDao;
import com.codecool.wallhaven.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoJDBC implements UserDao {
    private DataSource dataSource;

    public UserDaoJDBC(DataSource dataSource) {
        this.dataSource = dataSource;

    }
    @Override
    public List<User> getFriendsById(int userid) {
        List<User> friends = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT friends FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userid);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Array array = resultSet.getArray(1);
            Integer[] ints = (Integer[]) array.getArray();
            List<Integer> friendIds = Arrays.asList(ints);

            friendIds.forEach(id -> {
                User user = getUserById(id);
                if (user != null) {
                    friends.add(user);
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return friends;
    }

    @Override
    public User getUserById(int id) {
        User user = new User("asd", "asd", "asd");
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT username, password, email FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            user.setId(id);
            return user;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public String getIdByEmail(String email) {
        int userId = 0;
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return email;
            }
            userId = resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Integer.toString(userId);
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
    public void addFriend(int userId, int friendId) {
        List<User> actualFriends = getFriendsById(userId);
        List<Integer> ids = new ArrayList<>();
        actualFriends.forEach(friend -> {
            ids.add(friend.getId());
        });
        ids.add(friendId);
        List<Integer> newIds = ids.stream()
                .distinct()
                .collect(Collectors.toList());
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE users SET friends = ? WHERE id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            Array array = connection.createArrayOf("Integer", newIds.toArray());
            preparedStatement.setArray(1, array);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public boolean checkLogin(String email, String password) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT email, password FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @Override
    public String getUsernameByEmail(String email) {
        String username = "";
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT username FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return email;
            }
            username = resultSet.getString(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return username;
    }

    @Override
    public boolean isWallpaperFavorite(int id, String wallpaperId) {
        boolean favorite = false;
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT user_id, wallpaper_id FROM favorites WHERE user_id = ? AND wallpaper_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, wallpaperId);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            favorite = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return favorite;
    }

    @Override
    public void addFavorite(int id, String wallpaperId) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO favorites (user_id, wallpaper_id) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Array array = conn.createArrayOf("Integer", new Object[]{});
            statement.setInt(1, id);
            statement.setString(2, wallpaperId);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void addWallpaper(int id, String image) {
        image = image.replace("\"{", "");
        image = image.replace("}", "");
        image = image.replace("{\"image\":", "");
        image = image.replace("\"", "");

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO uploaded_wallpaper (user_id, wallpaper) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.setString(2, image);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
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
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, username, password, email FROM users";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            while (resultSet.next()) {
                User user = new User(resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                user.setId(resultSet.getInt(1));
                users.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }
}
