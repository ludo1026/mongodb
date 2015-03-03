package org.demo.dao;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import org.demo.domain.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

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

    private Datastore getUserDatastore() {
        try {
            MongoClient mongo = new MongoClient(this.host);
            Datastore ds = new Morphia().createDatastore(mongo, this.databaseName);
            return ds;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User user) {
        getUserDatastore().save(user);
    }

    public void remove(User user) {
        getUserDatastore().delete(QueryBuilder.start("_id").is(user.getLogin()).get());
    }

    public List<User> findAll() {
        List<User> users = getUserDatastore().find(User.class).asList();
        return users;
    }

    public User findByLogin(String login) {
        User user = getUserDatastore().find(User.class, "_id", login).get();
        return user;
    }

}
