import org.junit.*;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PostTest {
    private static DBHelper dbH;
    private static EntityManager em;
    private static User user;
    private Post post;

    @BeforeClass
    public static void setUpBefore() throws Exception {
        dbH = new DBHelper();
        em = dbH.getEntityManager();
        user = new User("Shiba");
        dbH.persistInATransaction(user);
    }

    @AfterClass
    public static void tearDownAfter() throws Exception {
        dbH.close();
    }

    @Before
    public void setUp() {
        post = new Post(user, "content");
        dbH.persistInATransaction(post);
    }

    @Test
    public void postConstructor() {
        User user = new User("SuchUser");
        dbH.persistInATransaction(user);
        Post post = new Post(user, "Much content");
        assertTrue(dbH.persistInATransaction(post));
        assertEquals("suchuser", post.getAuthor().getUsername());
        assertEquals("Much content", post.getContent());
        assertTrue(new Date().getTime() >= post.getDate().getTime());
        assertEquals(0, post.getVotes());
    }

    @Test
    public void setContent() throws Exception {
        String content = "Much content";
        post.setContent(content);
        assertTrue(dbH.persistInATransaction(post));
        assertEquals(content, post.getContent());
        content = generateContent(50000);
        post.setContent(content);
        assertTrue(dbH.persistInATransaction(post));
        assertEquals(content, post.getContent());
    }

    @Test
    public void upVote() throws Exception {
        post.upVote();
        assertTrue(dbH.persistInATransaction(post));
        assertEquals(1, post.getVotes());
        assertEquals(1, post.getUpVotes());
        assertEquals(0, post.getDownVotes());
    }

    @Test
    public void downVote() throws Exception {
        post.downVote();
        assertTrue(dbH.persistInATransaction(post));
        assertEquals(-1, post.getVotes());
        assertEquals(0, post.getUpVotes());
        assertEquals(1, post.getDownVotes());
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

    @Test
    public void invalidContent() {
        post.setContent(null);
        assertFalse(dbH.persistInATransaction(post));
        post.setContent("");
        assertFalse(dbH.persistInATransaction(post));
        post.setContent(generateContent(50001));
        assertFalse(dbH.persistInATransaction(post));
    }

    // Source: http://stackoverflow.com/a/4903603
    public String generateContent(int numberOfChars) {
        return new String(new char[numberOfChars]).replace("\0", "a");
    }

}