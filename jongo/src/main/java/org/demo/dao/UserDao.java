package org.demo.dao;

import com.mongodb.*;
import org.bson.types.ObjectId;
import org.demo.domain.User;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

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

    private MongoCollection getUserCollection() {
        try {
            Mongo mongo = new MongoClient(host);
            DB db = mongo.getDB(databaseName);
            Jongo jongo = new Jongo(db);
            return jongo.getCollection(USERS);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User user) {
        getUserCollection().save(user);
    }

    public void remove(User user) {
        getUserCollection().remove("{_id: '"+user.getLogin()+"'}");
    }

    public List<User> findAll() {
        MongoCursor<User> usersCursor = getUserCollection().find().as(User.class);
        List<User> users = new ArrayList<>();
        for(User user : usersCursor) {
            users.add(user);
        }
        return users;
    }

    public User findByLogin(String login) {
        User user = getUserCollection().findOne("{_id: '"+login+"'}").as(User.class);;
        return user;
    }

}
