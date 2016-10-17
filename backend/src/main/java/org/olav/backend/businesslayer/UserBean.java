package org.olav.backend.businesslayer;

import org.apache.commons.codec.digest.DigestUtils;
import org.olav.backend.datalayer.Address;
import org.olav.backend.datalayer.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.security.SecureRandom;

@Stateless
public class UserBean {

    //Dependency injection: the container will add it
    @PersistenceContext
    private EntityManager em;

    public UserBean(){}

    /**
     * Return created User object if the user was created successfully
     * Return null if for any reason it was not possible to create the user
     */
    public User registerNewUser(@NotNull String username, @NotNull String password, @NotNull String email, String name, Address address){
        if(isRegistered(username) || password.isEmpty()){
            return null;
        }

        User user = new User(username, address, name, email);

        //create a "strong" random string of at least 128 bits, needed for the "salt"
        String salt = getSalt();
        String hash = computeHash(password, salt);

        user.setSalt(salt);
        user.setHash(hash);

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

    /**
     *
     * @param userId
     * @param password
     * @return  {@code true} if a user with the given password exists
     */
    public boolean login(String userId, String password) {
        if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        User userDetails = getUser(userId);
        if (userDetails == null) {
            return false;
        }

        String hash = computeHash(password, userDetails.getSalt());

        boolean isOK = hash.equals(userDetails.getHash());
        return isOK;
    }


    //------------------------------------------------------------------------


    @NotNull
    protected String computeHash(String password, String salt){
        String combined = password + salt;
        String hash = DigestUtils.sha256Hex(combined);
        return hash;
    }

    @NotNull
    protected String getSalt(){
        SecureRandom random = new SecureRandom();
        int bitsPerChar = 5;
        int twoPowerOfBits = 32; // 2^5
        int n = 26;
        assert n * bitsPerChar >= 128;

        String salt = new BigInteger(n * bitsPerChar, random).toString(twoPowerOfBits);
        return salt;
    }

}
