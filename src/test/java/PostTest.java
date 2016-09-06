import org.junit.*;

import javax.persistence.EntityManager;

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
    public static void tearDown() throws Exception {
        dbH.close();
    }

    @Before
    public void setUp() {
        post = new Post("SuchUser", "Much content");
    }

    @Test
    public void testPostConstructor() {
        Post post = em.find(Post.class, this.post.getId());
        assertEquals("SuchUser", post.getAuthor());
        assertEquals("Much content", post.getContent());
        assertEquals("lol", post.getDate());
        assertEquals(0, post.getVotes());
    }

    @Test
    public void setContent() throws Exception {

    }

    @Test
    public void upVote() throws Exception {

    }

    @Test
    public void downVote() throws Exception {

    }

}