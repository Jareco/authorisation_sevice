package com.ms1.models;

import com.sun.jersey.spi.resource.Singleton;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User implements Serializable {

    private String firstname;
    private String lastname;
    private String email;
    int id;
    private String password;

    private long lastSession;
    static final long serialVersionUID = 5813215148936621843L;


    public User() {
    }


    public User(String email, String password) {
        this.email=email;
        this.password = password;
    }


    public User(String firstname, String lastname, String email, int id, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.id = id;
        this.password = password;

    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty
    public void setLastSession(long lastSession) {
        this.lastSession = lastSession;
    }


    @Column(name = "userlastsession")
    public long getLastSession() {
        return lastSession;
    }

    @Column(name = "userfirstname")
    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;

    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column(name = "userlastname")
    public String getLastname() {
        return lastname;
    }



    @Id
    @Column(name = "useremail", unique = true)
    public String getEmail() {
        return email;
    }


    @Column(name = "userid", unique = true)
    public int getId() {
        return id;
    }



    @JsonProperty
    @Column(name = "userpassword")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
