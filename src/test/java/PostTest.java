import org.junit.*;

import javax.persistence.EntityManager;

import java.sql.Timestamp;
import java.util.Date;

import static org.junit.Assert.*;

public class PostTest {
    private static DBHelper dbH;
    private static EntityManager em;
    private Post post;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        dbH = new DBHelper();
        em = dbH.getEntityManager();
    }

    @AfterClass
    public static void tearDownAfter() throws Exception {
        dbH.close();
    }

    @Before
    public void setUp() {
        post = new Post();
        dbH.persistInATransaction(post);
    }

    @Test
    public void testPostConstructor() {
        Post post = new Post("SuchUser", "Much content");
        assertTrue(dbH.persistInATransaction(post));
        assertEquals("SuchUser", post.getAuthor());
        assertEquals("Much content", post.getContent());
        assertTrue(new Date().getTime() >= post.getDate().getTime() );
        assertEquals(0, post.getVotes());
    }

    @Test
    public void setContent() throws Exception {
        post.setContent("Much content");
        assertTrue(dbH.persistInATransaction(post));
        assertEquals("Much content", post.getContent());
    }

    @Test
    public void upVote() throws Exception {
        post.upVote();
        assertTrue(dbH.persistInATransaction(post));
        assertEquals(1, post.getVotes());
    }

    @Test
    public void downVote() throws Exception {
        post.downVote();
        assertTrue(dbH.persistInATransaction(post));
        assertEquals(-1, post.getVotes());
    }

}