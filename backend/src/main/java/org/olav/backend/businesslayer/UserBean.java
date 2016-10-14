package org.olav.backend.businesslayer;

import org.olav.backend.datalayer.Address;
import org.olav.backend.datalayer.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

@Stateless
public class UserBean {

    //Dependency injection: the container will add it
    @PersistenceContext
    private EntityManager em;

    public UserBean(){}

    //all methods in a EJB are wrapped in a transaction, with automated rollback if exceptions
    public User registerNewUser(@NotNull String username, @NotNull String email, String name, Address address){
        if(isRegistered(username)){
            return null;
        }

        User user = new User(username, address, name, email);
        em.persist(user);
        return user;
    }

    public boolean isRegistered(@NotNull String username){
        return getUser(username) != null;
    }

    public int getNumberOfUsers(){
        Query query = em.createQuery("select count(u) from User u");
        return ((Number)query.getSingleResult()).intValue();
    }

    public User getUser(@NotNull String username) {
        return em.find(User.class, username.toLowerCase());
    }

}
