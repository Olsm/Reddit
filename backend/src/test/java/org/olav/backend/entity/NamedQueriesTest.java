package org.olav.backend.entity;

import org.junit.*;
import org.olav.backend.db.DBHelper;
import org.olav.backend.entity.Address;
import org.olav.backend.entity.Post;
import org.olav.backend.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NamedQueriesTest {
    private DBHelper dbH;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        dbH = new DBHelper();
        em = dbH.getEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        dbH.close();
    }

    @Test
    public void testGetCountries(){
        Query query = em.createNamedQuery(User.GET_COUNTRIES);
        List<String> countries = query.getResultList();
        assertEquals(0, countries.size());

        User user = new User("SuchUser", new Address("Oslo", "Norway"), "Shiba", "shiba@inu.wow");
        dbH.persistInATransaction(user);
        countries = query.getResultList();
        assertEquals(1, countries.size());
        assertEquals("Norway", countries.get(0));
    }

    @Test
    public void testSumUsers(){
        Query query = em.createNamedQuery(User.SUM_USERS);
        int result = ((Number)query.getSingleResult()).intValue();
        assertEquals(0, result);

        User user = new User("SuchUser");
        dbH.persistInATransaction(user);
        result = ((Number)query.getSingleResult()).intValue();
        assertEquals(1, result);
    }

    @Test
    public void testSumUsersInNorway(){
        Query query = em.createNamedQuery(User.SUM_USERS_IN_COUNTRY);
        query.setParameter("country", "Norway");
        int result = ((Number)query.getSingleResult()).intValue();
        assertEquals(0, result);

        dbH.persistInATransaction(new User("SuchUser", new Address("Oslo", "Norway"), "Shiba", "shiba@inu.wow"));
        result = ((Number)query.getSingleResult()).intValue();
        assertEquals(1, result);
    }

    @Test
    public void testSumPosts() {
        Query query = em.createNamedQuery(Post.SUM_POSTS);
        int result = ((Number)query.getSingleResult()).intValue();
        assertEquals(0, result);

        User user = new User("SuchUser");
        dbH.persistInATransaction(user);
        dbH.persistInATransaction(new Post(user, "content"));
        result = ((Number)query.getSingleResult()).intValue();
        assertEquals(1, result);
    }

    @Test
    public void testSumPostsInNorway() {
        Query query = em.createNamedQuery(Post.SUM_POSTS_IN_NORWAY);
        int result = ((Number)query.getSingleResult()).intValue();
        assertEquals(0, result);

        User user = new User("SuchUser", new Address("Oslo", "Norway"), "Shiba", "shiba@inu.wow");
        dbH.persistInATransaction(user);
        dbH.persistInATransaction(new Post(user, "content"));
        result = ((Number)query.getSingleResult()).intValue();
        assertEquals(1, result);
    }

    /*
    @Test
    public void testTopTenUsers(){
        Query query = em.createNamedQuery(User.TOP_TEN_USERS);
        System.out.println(query.toString());
        List<User> users = query.getResultList();
        assertEquals(0, users.size());

        for (int i = 0; i < 11; i++) {
            dbH.persistInATransaction(new User("Shiba" + i));
        }
        User user = new User("SuchUser");
        dbH.persistInATransaction(user);
        Post post = new Post(user, "");
        dbH.persistInATransaction(post);

        users = query.getResultList();
        assertEquals(10, users.size());
        assertEquals(user, users.get(0));
    } */
}
