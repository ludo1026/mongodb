package org.demo.dao;

import com.mongodb.*;
import org.demo.domain.User;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO : Users
 */
public class UserDao {

    public static String COLLECTION = "users";

    private final String host;
    private final String databaseName;

    public UserDao(String host, String databaseName) {
        this.host = host;
        this.databaseName = databaseName;
    }

    private MongoTemplate getMongoTemplate() {
        try {
            Mongo mongo = new MongoClient(host);
            MongoTemplate mongoTemplate = new MongoTemplate(mongo, databaseName);
            return mongoTemplate;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User user) {
        getMongoTemplate()
                .save(user, COLLECTION);
    }

    public void remove(User user) {
        getMongoTemplate()
                .remove(user, COLLECTION);
    }

    public List<User> findAll() {
        return getMongoTemplate()
                .findAll(User.class, COLLECTION);
    }

    public User findByLogin(String login) {
        return getMongoTemplate()
                .findById(login, User.class, COLLECTION);
    }

}
