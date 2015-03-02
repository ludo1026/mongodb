package org.demo.domain;

import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User
 */
public class User implements Serializable {

    @Id
    private String login;
    private String password;
    private String mail;
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
