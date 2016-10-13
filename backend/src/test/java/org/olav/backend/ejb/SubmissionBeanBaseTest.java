package org.olav.backend.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olav.backend.entity.Comment;
import org.olav.backend.entity.Post;
import org.olav.backend.entity.User;

import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class SubmissionBeanBaseTest {

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.olav.backend.ejb","org.olav.backend.entity")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private PostBean postBean;
    @EJB
    private UserBean userBean;

    private User user;
    Post post;
    private String content;
    private static int counter;

    @Before
    public void setupBefore() {
        user = userBean.registerNewUser("username" + counter++, "such@mail.com", "Shiba Inu", null);
        content = "test";
        post = postBean.registerPost(user, content);
    }


    @Test
    public void registerComment() throws Exception {
        post = postBean.registerComment(post, user, content);
        Comment comment = post.getComments().get(0);
        assertEquals(user, comment.getAuthor());
        assertEquals(content, comment.getContent());
        assertEquals(1, post.getComments().size());
        assertEquals(comment, post.getComments().get(0));
    }

    @Test
    public void upVote() {
        int votes = post.getVotes();
        assertEquals(0, post.getUpVotes());
        post.upVote();
        assertEquals(1, post.getUpVotes());
        assertEquals(votes + 1, post.getVotes());
    }

    @Test
    public void downVote() {
        int votes = post.getVotes();
        assertEquals(0, post.getDownVotes());
        post.downVote();
        assertEquals(1, post.getDownVotes());
        assertEquals(votes - 1, post.getVotes());
    }

}