package org.demo.dao;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.demo.MongoDBConfiguration;
import org.demo.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoDBConfiguration.class)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void before() throws Exception {
        Mongo mongo = new MongoClient();
        mongo.dropDatabase("test");
    }

    @Test
    public void testUserEmpty() {
        // Given
        User user = new User()
                .setLogin("testUserEmpty login");

        // When
        userRepository.save(user);
        User userSaved = userRepository.findOne(user.getLogin());

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
        userRepository.save(user);
        User userSaved = userRepository.findOne(user.getLogin());

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
        userRepository.save(user);
        User userSaved = userRepository.findOne(user.getLogin());

        // Then
        Assert.assertEquals("testUserTwoRoles login", userSaved.getLogin());
        Assert.assertEquals("test@test.com", userSaved.getMail());
        Assert.assertEquals("test password", userSaved.getPassword());
        Assert.assertEquals(2, userSaved.getRoles().size());
        Assert.assertEquals("ADMIN", userSaved.getRoles().get(0));
        Assert.assertEquals("USER", userSaved.getRoles().get(1));

    }

}
