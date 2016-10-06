import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

@EJB @Stateless
public class UserBean {

    //Dependency injection: the container will add it
    @PersistenceContext
    private EntityManager em;

    public UserBean(){}

    //all methods in a EJB are wrapped in a transaction, with automated rollback if exceptions
    public void registerNewUser(@NotNull String username, @NotNull String email, String name, Address address){
        if(isRegistered(username)){
            return;
        }

        User user = new User(username, address, name, email);
        em.persist(user);
    }

    public boolean isRegistered(@NotNull String username){
        return em.find(User.class, username) != null;
    }

    public long getNumberOfUsers(){
        Query query = em.createQuery("select count(u) from User u");
        return (Long) query.getSingleResult();
    }

}
