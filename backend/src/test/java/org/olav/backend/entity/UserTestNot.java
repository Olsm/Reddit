package org.olav.backend.entity;

import org.junit.*;
import org.olav.backend.db.DBHelper;
import org.olav.backend.entity.Address;
import org.olav.backend.entity.User;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

public class UserTestNot {
    private static DBHelper dbH;
    private static EntityManager em;
    private static int userNameCounter = 0;
    private User user;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        dbH = new DBHelper();
        em = dbH.getEntityManager();
    }

    @Before
    public void setUp(){
        user = new User("SuchUser" + userNameCounter++);
        dbH.persistInATransaction(user);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        dbH.close();
    }

    @Test
    public void testUserConstructor() {
        User u = new User("souser", new Address("SuchCity", "VeryCountry"), "So Name", "very@shibe.wow");
        assertTrue(dbH.persistInATransaction(u));
        User user = em.find(User.class, u.getUsername());
        assertEquals("souser", user.getUsername());
        assertEquals("SuchCity", user.getAddress().getCity());
        assertEquals("VeryCountry", user.getAddress().getCountry());
        assertEquals("So Name", user.getName());
        assertEquals("very@shibe.wow", user.getEmail());
    }

    @Test
    public void setAddress() throws Exception {
        Address address = new Address("Oslo", "Norway");
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

    @Test
    public void setInvalidEmail() throws Exception {
        user.setEmail("invalidemail.com");
        assertFalse(dbH.persistInATransaction(user));
        user.setEmail("anotherinvalidemail");
        assertFalse(dbH.persistInATransaction(user));
        user.setEmail("stillThisIs@nInvalidEmail");
        assertFalse(dbH.persistInATransaction(user));
    }

    @Test
    public void setInvalidUsername() throws Exception {
        user = new User("");
        assertFalse(dbH.persistInATransaction(user));
        user = new User("iv");
        assertFalse(dbH.persistInATransaction(user));
        user = new User("qwertyuiopåasdfg");
        assertFalse(dbH.persistInATransaction(user));
        user = new User("#test");
        assertFalse(dbH.persistInATransaction(user));
    }

}