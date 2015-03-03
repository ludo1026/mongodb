package org.demo.dao;

import com.mongodb.*;
import org.demo.domain.User;
import org.mongojack.JacksonDBCollection;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO : Users
 */
public class UserDao {

    public static String USERS = "users";

    private final String host;
    private final String databaseName;

    public UserDao(String host, String databaseName) {
        this.host = host;
        this.databaseName = databaseName;
    }

    private JacksonDBCollection<User, String> getUserJacksonDBCollection() {
        try {
            Mongo mongo = new MongoClient(host);
            DB db = mongo.getDB(databaseName);
            DBCollection dbCollection = db.getCollection(USERS);
            JacksonDBCollection<User, String> jacksonDBCollection =
                    JacksonDBCollection.wrap(dbCollection, User.class, String.class);
            return jacksonDBCollection;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User user) {
        getUserJacksonDBCollection().save(user);
    }

    public void remove(User user) {
        getUserJacksonDBCollection().remove(QueryBuilder.start("_id").is(user.getLogin()).get());
    }

    public List<User> findAll() {
        List<User> users = getUserJacksonDBCollection().find().toArray();
        return users;
    }

    public User findByLogin(String login) {
        User user = getUserJacksonDBCollection().findOne(QueryBuilder.start("_id").is(login).get());
        return user;
    }

}
