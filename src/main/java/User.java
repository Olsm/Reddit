import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Embedded
    DBHelper dbHelper;
    @Id
    private String username;
    private String address;
    private String name;
    private String email;

    public User(String username, String address, String name, String email) {
        this.username = username;
        this.address = address;
        this.name = name;
        this.email = email;

        dbHelper = new DBHelper();
        dbHelper.persistInATransaction(this);
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

    public boolean setAddress(String address) {
        this.address = address;
        return dbHelper.persistInATransaction(this);
    }

    public boolean setName(String name) {
        this.name = name;
        return dbHelper.persistInATransaction(this);
    }

    public boolean setEmail(String email) {
        this.email = email;
        return dbHelper.persistInATransaction(this);
    }

}
