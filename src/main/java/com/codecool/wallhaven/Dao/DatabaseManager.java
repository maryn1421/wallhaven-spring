package com.codecool.wallhaven.Dao;

import com.codecool.wallhaven.Dao.implementation.UserDaoJDBC;
import com.codecool.wallhaven.model.User;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

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
}
