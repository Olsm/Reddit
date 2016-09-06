import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

public class UserTest {
    private static DBHelper dbH;
    private User user;
    private static EntityManager em;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        dbH = new DBHelper();
        em = dbH.getEntityManager();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        dbH.close();
    }

    @Before
    public void setUp() {
        user = new User("SuchUser");
    }

    @Test
    public void testUserConstructor() {
        new User("SuchUser", "Very Address", "So Name", "very@shibe.wow");
        User user = em.find(User.class, "SuchUser");
        assertEquals("SuchUser", user.getUsername());
        assertEquals("Very Address", user.getAddress());
        assertEquals("So Name", user.getName());
        assertEquals("very@shibe.wow", user.getEmail());
    }

    @Test
    public void setAddress() throws Exception {
        String address = "Dyretråkket 24, 1251 Oslo";
        assertTrue(user.setAddress(address));
        assertEquals(address, user.getAddress());
    }

    @Test
    public void setName() throws Exception {
        String name = "Olav Småriset";
        assertTrue(user.setName(name));
        assertEquals(name, user.getName());
    }

    @Test
    public void setEmail() throws Exception {
        String email = "olavolsm@gmail.com";
        assertTrue(user.setEmail(email));
        assertEquals(email, user.getEmail());
    }

}