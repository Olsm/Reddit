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
    public void testUser() {

    }

    @Test
    public void setAddress() throws Exception {
        String address = "Dyretråkket 24, 1251 Oslo";
        user.setAddress(address);
        assertEquals(address, user.getAddress());
        em.clear();
        assertEquals(address, em.find(User.class, user.getUsername()).getAddress());
    }

    @Test
    public void setName() throws Exception {

    }

    @Test
    public void setEmail() throws Exception {

    }

}