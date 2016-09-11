import org.junit.*;

import javax.persistence.EntityManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        User user = new User("SuchUser");
        dbH.persistInATransaction(user);
        Post post = new Post(user, "Much content");
        assertTrue(dbH.persistInATransaction(post));
        assertEquals("SuchUser", post.getAuthor().getUsername());
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

    @Test
    public void setComment() {
        List<Comment> comments = new ArrayList<Comment>();
        assertEquals(comments, post.getComments());
        Comment comment = new Comment();
        dbH.persistInATransaction(comment);
        comments.add(comment);
        post.setComments(comments);
        assertTrue(dbH.persistInATransaction(post));
        assertEquals(comments, post.getComments());
    }

}