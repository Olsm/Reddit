import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class UserTest {
    private static DBHelper dbH;
    private static User user;
    private static EntityManager em;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        dbH = new DBHelper();
        em = dbH.getEntityManager();
        user = new User("SuchUser");
        dbH.persistInATransaction(user);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        dbH.close();
    }

    @Test
    public void testUserConstructor() {
        User u = new User("SoUser", "Very Address", "So Name", "very@shibe.wow");
        assertTrue(dbH.persistInATransaction(u));
        User user = em.find(User.class, u.getUsername());
        assertEquals("SoUser", user.getUsername());
        assertEquals("Very Address", user.getAddress());
        assertEquals("So Name", user.getName());
        assertEquals("very@shibe.wow", user.getEmail());
    }

    @Test
    public void setAddress() throws Exception {
        String address = "Dyretråkket 24, 1251 Oslo";
        user.setAddress(address);
        assertTrue(dbH.persistInATransaction(user));
        assertEquals(address, user.getAddress());
    }

    @Test
    public void setName() throws Exception {
        String name = "Olav Småriset";
        user.setName(name);
        assertTrue(dbH.persistInATransaction(user));
        assertEquals(name, user.getName());
    }

    @Test
    public void setEmail() throws Exception {
        String email = "olavolsm@gmail.com";
        user.setEmail(email);
        assertTrue(dbH.persistInATransaction(user));
        assertEquals(email, user.getEmail());
    }

}