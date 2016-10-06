package org.olav.backend;

import org.junit.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CommentTest {
    private static DBHelper dbH;
    private static EntityManager em;
    private static User user;
    private Comment comment;

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
        comment = new Comment(user, "content");
        dbH.persistInATransaction(comment);
    }

    @Test
    public void testCommentConstructor() {
        User user = new User("SuchUser");
        dbH.persistInATransaction(user);
        Comment comment = new Comment(user, "Much content");
        assertTrue(dbH.persistInATransaction(comment));
        assertEquals(user, comment.getAuthor());
        assertEquals("Much content", comment.getContent());
        assertTrue(new Date().getTime() >= comment.getDate().getTime() );
        assertEquals(0, comment.getVotes());
    }

    @Test
    public void setContent() throws Exception {
        comment.setContent("Much content");
        assertTrue(dbH.persistInATransaction(comment));
        assertEquals("Much content", comment.getContent());
    }

    @Test
    public void upVote() throws Exception {
        comment.upVote();
        assertTrue(dbH.persistInATransaction(comment));
        assertEquals(1, comment.getVotes());
    }

    @Test
    public void downVote() throws Exception {
        comment.downVote();
        assertTrue(dbH.persistInATransaction(comment));
        assertEquals(-1, comment.getVotes());
    }

    @Test
    public void setComment() {
        List<Comment> comments = new ArrayList<Comment>();
        assertEquals(comments, comment.getComments());
        Comment commentComment = new Comment(user, "content");
        dbH.persistInATransaction(commentComment);
        comments.add(commentComment);
        comment.setComments(comments);
        assertTrue(dbH.persistInATransaction(comment));
        assertEquals(comments, comment.getComments());
    }
}