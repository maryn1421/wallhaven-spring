package com.codecool.wallhaven.Dao.implementation;

import com.codecool.wallhaven.Dao.DatabaseManager;
import com.codecool.wallhaven.Dao.UserDao;
import com.codecool.wallhaven.model.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class UserDaoJDBCTest {
    @Before
    public void setup() {
        JDBCTest test = new JDBCTest();
        test.userDao = mock(UserDaoJDBC.class);
    }


    @Test
    void testAddUser() {
        User user = new User("asd", "asd", "asd");
        JDBCTest test = new JDBCTest();
        test.userDao = mock(UserDaoJDBC.class);
        doNothing().when(test.userDao).addUser(isA(User.class));

        test.userDao.addUser(user);

        verify(test.userDao, times(1)).addUser(user);

    }

    /*
       @Test
	public void whenAddCalledVerified() {
	    MyList myList = mock(MyList.class);
	    doNothing().when(myList).add(isA(Integer.class), isA(String.class));
	    myList.add(0, "");

	    verify(myList, times(1)).add(0, "");
	}
     */


}