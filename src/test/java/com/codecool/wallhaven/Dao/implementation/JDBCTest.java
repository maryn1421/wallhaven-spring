package com.codecool.wallhaven.Dao.implementation;

import com.codecool.wallhaven.Dao.DatabaseManager;
import com.codecool.wallhaven.Dao.UserDao;

import javax.sql.DataSource;

public class JDBCTest {
    UserDao userDao;

    public void setUserDao(DataSource dataSource) {
        this.userDao = new UserDaoJDBC(dataSource);
    }
}
