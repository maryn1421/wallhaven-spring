package com.codecool.wallhaven.Dao;

import com.codecool.wallhaven.Dao.implementation.UserDaoJDBC;
import com.codecool.wallhaven.model.User;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class DatabaseManager {
    private UserDao userDao;
   // private GameStateDao  gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        userDao = new UserDaoJDBC(dataSource);
       // gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void addUser(User user) {
      userDao.addUser(user);
    }

    public List<User> getFriendsById(int id) {
        return userDao.getFriendsById(id);
    }

    public List<User> getFriendsList() {
        return userDao.getFriendsById(1);
    }

    public void addFriend(int userId, int friendId) {
        userDao.addFriend(userId, friendId);
    }

    public User getUserById (int id) {
        return userDao.getUserById(id);
    }



    public boolean checkLogin(String email, String password) {
        return userDao.checkLogin(email, password);
    }

    /*public void saveGameState(String currentMap, Player player) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(timestamp.getTime());
        PlayerModel model = new PlayerModel(player);
        GameState gameState = new GameState(currentMap, date, model);
        gameStateDao.add(gameState);
    }

     */

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName =  System.getenv("DB_NAME");
        String user = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        System.out.println(dbName);

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }

    public List<User> getAllUser() {
        return userDao.getAllUser();

    }

    public String getIdByEmail (String email) {
        return userDao.getIdByEmail(email);
    }

    public String getUsernameByEmail(String email) {
        return userDao.getUsernameByEmail(email);
    }

    public boolean isWallpaperFavorite(int id, String wallpaperId) {
        return userDao.isWallpaperFavorite(id, wallpaperId);
    }

    public void addFavorite(int id, String wallpaperId) {
        userDao.addFavorite(id, wallpaperId);
    }

    public void addWallpaper(int id, String image) {
        userDao.addWallpaper(id, image);
    }

    public List<String> getFavouritesByUserID(int userID) {
        return userDao.getFavouritesByUserID(userID);
    }

    public List<String> getUploaded(int id) {
        return userDao.getUploaded(id);
    }
}
