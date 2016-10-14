package org.olav.backend.datalayer;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Email;

@NamedQueries({
        @NamedQuery(name = User.SUM_USERS, query = "select count(u) from User u"),
        @NamedQuery(name = User.SUM_USERS_IN_COUNTRY, query = "select count(u) from User u where u.address.country = :country"),
        @NamedQuery(name = User.GET_COUNTRIES, query = "select u.address.country from User u"),
        //@NamedQuery(name = User.TOP_TEN_USERS, query = "select u from Post p, Comment c left join p.author as u " +
        //                                                   "group by u order by count(p) + count(c)")
        //@NamedQuery(name = User.TOP_TEN_USERS, query = "select count(p) as posts " +
        //        "from Post p join p.author order by posts")
        //@NamedQuery(name = User.TOP_TEN_USERS, query = "select u, count(p) as posts, count(c) as comments " +
        //        "from User u join Post p join Comment c order by posts + comments")
})

@Entity
public class User {

    public static final String SUM_USERS = "SUM_USERS";
    public static final String SUM_USERS_IN_COUNTRY = "SUM_USERS_IN_COUNTRY";
    public static final String GET_COUNTRIES = "GET_COUNTRIES";
    public static final String TOP_TEN_USERS = "TOP_TEN_USERS";

    @Id @Pattern(regexp = "^(?i)[A-Z0-9_-]{3,15}$")
    private String username;
    @Embedded private Address address;
    private String name;
    @Email @Pattern(regexp = ".*?\\.(?i)[A-Z0-9].*") // valid email must end with .something
    private String email;

    public User(String username, Address address, String name, String email) {
        if (username != null) username = username.toLowerCase();
        if (email != null) email = email.toLowerCase();
        this.username = username;
        this.address = address;
        this.name = name;
        this.email = email;
    }

    public User (String username) {
        this(username, null, null, null);
    }

    public User() {
        this(null, null, null, null);
    }

    public String getUsername() {
        return username;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
