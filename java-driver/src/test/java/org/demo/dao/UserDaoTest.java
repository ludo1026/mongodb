package org.demo.dao;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.demo.domain.User;
import org.demo.dao.UserDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test
 */
public class UserDaoTest {

    String host = "localhost";
    String databaseName = "test";

    UserDao userDao = new UserDao(host, databaseName);

    @Before
    public void before() throws Exception {
        Mongo mongo = new MongoClient();
        mongo.dropDatabase(databaseName);
    }

    @Test
    public void testUserEmpty() {
        // Given
        User user = new User()
                .setLogin("testUserEmpty login");

        // When
        userDao.save(user);
        User userSaved = userDao.findByLogin(user.getLogin());

        // Then
        Assert.assertEquals("testUserEmpty login", userSaved.getLogin());
    }

    @Test
    public void testUserOneRole() {
        // Given
        User user = new User()
                .setLogin("testUserOneRole login")
                .setMail("test@test.com")
                .setPassword("test password")
                .addRole("ADMIN");

        // When
        userDao.save(user);
        User userSaved = userDao.findByLogin(user.getLogin());

        // Then
        Assert.assertEquals("testUserOneRole login", userSaved.getLogin());
        Assert.assertEquals("test@test.com", userSaved.getMail());
        Assert.assertEquals("test password", userSaved.getPassword());
        Assert.assertEquals(1, userSaved.getRoles().size());
        Assert.assertEquals("ADMIN", userSaved.getRoles().get(0));
    }

    @Test
    public void testUserTwoRoles() {
        // Given
        User user = new User()
                .setLogin("testUserTwoRoles login")
                .setMail("test@test.com")
                .setPassword("test password")
                .addRole("ADMIN")
                .addRole("USER");

        // When
        userDao.save(user);
        User userSaved = userDao.findByLogin(user.getLogin());

        // Then
        Assert.assertEquals("testUserTwoRoles login", userSaved.getLogin());
        Assert.assertEquals("test@test.com", userSaved.getMail());
        Assert.assertEquals("test password", userSaved.getPassword());
        Assert.assertEquals(2, userSaved.getRoles().size());
        Assert.assertEquals("ADMIN", userSaved.getRoles().get(0));
        Assert.assertEquals("USER", userSaved.getRoles().get(1));

    }

}
