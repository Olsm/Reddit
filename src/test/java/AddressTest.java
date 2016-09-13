import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

public class AddressTest {
    private static DBHelper dbH;
    private static EntityManager em;
    private static User user;
    private Address address;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        dbH = new DBHelper();
        em = dbH.getEntityManager();
        user = new User("Shibe");
    }

    @AfterClass
    public static void tearDownAfter() throws Exception {
        dbH.close();
    }

    @Before
    public void setUp() {
        address = new Address();
        user.setAddress(address);
        dbH.persistInATransaction(user);
    }

    @Test
    public void setCity() throws Exception {
        address.setCity("SuchCity");
        assertTrue(dbH.persistInATransaction(user));
        assertEquals("SuchCity", address.getCity());
    }

    @Test
    public void setCountry() throws Exception {
        address.setCountry("VeryCountry");
        assertTrue(dbH.persistInATransaction(user));
        assertEquals("VeryCountry", address.getCountry());
    }

}