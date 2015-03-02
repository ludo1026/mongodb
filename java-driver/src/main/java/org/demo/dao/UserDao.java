package org.demo.dao;

import com.mongodb.*;
import org.demo.domain.User;

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

    private DBCollection getUserDBCollection() {
        try {
            Mongo mongo = new MongoClient(host);
            DB db = mongo.getDB(databaseName);
            return db.getCollection(USERS);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    private DBObject getDBObjectFromUser(User user) {
        BasicDBObject userDBObject = new BasicDBObject();
        userDBObject.append("_id", user.getLogin());
        userDBObject.append("password", user.getPassword());
        userDBObject.append("mail", user.getMail());
        BasicDBList rolesDBObject = new BasicDBList();
        for(String role : user.getRoles()) {
            rolesDBObject.add(role);
        }
        userDBObject.append("roles", user.getRoles());
        return userDBObject;
    }

    private User getUserFromDBObject(DBObject userDBObject) {
        User user = new User();
        if(userDBObject.get("_id") != null) {
            user.setLogin(userDBObject.get("_id").toString());
        }
        if(userDBObject.get("password") != null) {
            user.setPassword(userDBObject.get("password").toString());
        }
        if(userDBObject.get("mail") != null) {
            user.setMail(userDBObject.get("mail").toString());
        }
        if(userDBObject.get("roles") != null) {
            BasicDBList rolesDBObject = (BasicDBList) userDBObject.get("roles");
            for (Object roleObject : rolesDBObject) {
                user.addRole(roleObject.toString());
            }
        }
        return user;
    }

    public void save(User user) {
        DBObject userDBObject = getDBObjectFromUser(user);
        getUserDBCollection().save(userDBObject);
    }

    public void remove(User user) {
        getUserDBCollection().remove(QueryBuilder.start("_id").is(user.getLogin()).get());
    }

    public List<User> findAll() {
        try(DBCursor cursor = getUserDBCollection().find()) {
            List<User> users = new ArrayList<User>();
            while (cursor.hasNext()) {
                DBObject userDBObject = cursor.next();
                User user = getUserFromDBObject(userDBObject);
                users.add(user);
            }
            return users;
        }
    }

    public User findByLogin(String login) {
        DBObject userDBObject = getUserDBCollection().findOne(QueryBuilder.start("_id").is(login).get());
        if(userDBObject == null) {
            return null;
        }
        return getUserFromDBObject(userDBObject);
    }

}
