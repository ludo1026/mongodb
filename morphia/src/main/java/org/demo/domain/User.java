package org.demo.domain;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User
 */
@Entity("users")
public class User implements Serializable {

    @Id
    private String login;
    private String password;
    private String mail;
    @Reference
    private List<String> roles = new ArrayList<String>();

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public User addRole(String role) {
        this.roles.add(role);
        return this;
    }

}
