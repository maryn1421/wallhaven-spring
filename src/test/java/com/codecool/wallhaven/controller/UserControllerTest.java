package com.codecool.wallhaven.controller;

import com.codecool.wallhaven.model.User;
import com.codecool.wallhaven.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserControllerTest {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository repository;

    @Test
    public void getAvailableUsers() {


    }

    @Test
    public void should_store_a_user() {
        List<Long> friendIds = new ArrayList<>();
        friendIds.add(2L);
        User user = User.builder().email("asd@asd.com").friends(friendIds).name("test").password("asd").build();
        repository.save(user);

        assertEquals(user, repository.getUserById(user.getId()).get());


    }

    @Test
    public void should_find_no_users_if_repository_is_empty() {
        List<User> users = repository.findAll();
        assertEquals(users.size(), 0);

    }

    @Test
    public void should_find_all_users() {
        User tut1 = User.builder().email("asd@asd.com").friends(new ArrayList<Long>()).name("asd").password("asdsad").build();
        entityManager.persist(tut1);

        User tut2 = User.builder().email("asd@asd.com").friends(new ArrayList<Long>()).name("asd").password("asdsad").build();
        entityManager.persist(tut2);

        User tut3 = User.builder().email("asd@asd.com").friends(new ArrayList<Long>()).name("asd").password("asdsad").build();
        entityManager.persist(tut3);

        List<User> tutorials = repository.findAll();

        assertEquals(tutorials.size(), 3);
    }


    @Test
    public void addFriend() {
        List<Long> user1friends = new ArrayList<>();
        List<Long> user2friends = new ArrayList<>();

        User user1 = User.builder().email("asd@asd.com").friends(user1friends).name("user1").password("passworduser1").build();


        User user2 = User.builder().email("asd@asd.com").friends(user2friends).name("user2").password("passworduser2").build();
        entityManager.persist(user2);

        user1.getFriends().add(user2.getId());
        entityManager.persist(user1);

        assertEquals(repository.getFriendsById(user1.getId()).size(), 1);

    }

    @Test
    public void getFriends() {
        List<Long> user1friends = new ArrayList<>();
        List<Long> user2friends = new ArrayList<>();

        User user1 = User.builder().email("asd@asd.com").friends(user1friends).name("user1").password("passworduser1").build();


        User user2 = User.builder().email("asd@asd.com").friends(user2friends).name("user2").password("passworduser2").build();
        entityManager.persist(user2);

        user1.getFriends().add(user2.getId());
        entityManager.persist(user1);

        assertEquals(repository.getFriendsById(user1.getId()).size(), 1);

    }

    @Test
    public void getAllUser() {

        List<Long> user1friends = new ArrayList<>();
        List<Long> user2friends = new ArrayList<>();

        User user1 = User.builder().email("asd@asd.com").friends(user1friends).name("user1").password("passworduser1").build();
        entityManager.persist(user1);

        User user2 = User.builder().email("asd@asd.com").friends(user2friends).name("user2").password("passworduser2").build();
        entityManager.persist(user2);
        User user3 = User.builder().email("asd@asd.com").friends(user2friends).name("user2").password("passworduser2").build();
        entityManager.persist(user3);

        assertEquals(repository.findAll().size(), 3);


    }

    @Test
    public void registerUser() {
    }

    @Test
    public void getFriendsById() {

    }

    @Test
    public void getUserDataById() {
    }

    @Test
    public void getUserById() {
    }

    @Test
    public void getUsernameByEmail() {
        List<Long> user1friends = new ArrayList<>();

        User user1 = User.builder().email("asd@asd.com").friends(user1friends).name("user1").password("passworduser1").build();
        entityManager.persist(user1);


        assertEquals(user1.getName(), repository.findByEmail(user1.getEmail()).get().getName());

    }

    @Test
    public void isEmailAvailable() {
        List<Long> user1friends = new ArrayList<>();

        User user1 = User.builder().email("asd@asd.com").friends(user1friends).name("user1").password("passworduser1").build();
        entityManager.persist(user1);

        assertTrue(repository.findByEmail(user1.getEmail()).isPresent());
    }

    @Test
    public void isNameAvailable() {
        List<Long> user1friends = new ArrayList<>();

        User user1 = User.builder().email("asd@asd.com").friends(user1friends).name("user1").password("passworduser1").build();
        entityManager.persist(user1);

        assertTrue(repository.findByName(user1.getName()).isPresent());

    }

    @Test
    public void updateName() {
    }

    @Test
    public void updateEmail() {
    }

    @Test
    public void login1() {
    }

    @Test
    public void getUserByEmail() {
    }
}