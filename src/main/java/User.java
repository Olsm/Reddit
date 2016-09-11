import javax.persistence.*;

@NamedQueries(
        @NamedQuery(name = User.SUM_USERS, query = "select sum(u) from User u"),
        @NamedQuery(name = User.SUM_USERS_IN_NORWAY, query = "select sum(u) from User u where u.address.country = 'Norway'"),
        @NamedQuery(name = User.GET_COUNTRIES, query = "select u.address.country from User u"),
        @NamedQuery(name = User.TOP_TEN_USERS, query = "select u from User u order by " +
                "((select sum(p) from Post where author = u) + " + "(select sum(c) from Comment where author = u)) limit 10")
)

@Entity
public class User {

    public static final String SUM_USERS = "SUM_USERS";
    public static final String SUM_USERS_IN_NORWAY = "SUM_USERS_IN_NORWAY";
    public static final String GET_COUNTRIES = "GET_COUNTRIES";
    public static final String TOP_TEN_USERS = "TOP_TEN_USERS";

    @Id
    private String username;
    private String address; //TODO: Make address embedded
    private String name;
    private String email;

    public User(String username, String address, String name, String email) {
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

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
