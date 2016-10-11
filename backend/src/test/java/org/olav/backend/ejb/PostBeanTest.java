package org.olav.backend.ejb;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olav.backend.entity.Address;
import org.olav.backend.entity.Comment;
import org.olav.backend.entity.Post;
import org.olav.backend.entity.User;

import javax.ejb.EJB;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PostBeanTest {

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
    private String content;
    private static int counter;

    @Before
    public void setupBefore() {
        user = userBean.registerNewUser("username" + counter++, "such@mail.com", "Shiba Inu", null);
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus volutpat turpis vitae bibendum auctor. Aliquam posuere tempus hendrerit. Sed at leo massa. Aenean eget libero est. Cras semper neque vitae nulla interdum rutrum. Duis est augue, vestibulum et justo eget, commodo consequat nulla. Sed elit libero, tincidunt eget finibus quis, cursus non ex. Nam in luctus ante. Quisque odio orci, scelerisque vel fringilla eget, suscipit ut sapien. Vivamus elementum eros vitae risus imperdiet aliquet. In ac dui sem. Morbi quis eros eleifend, feugiat tellus sed, malesuada massa.";
    }

    @Test
    public void registerPost() throws Exception {
        Post post = postBean.registerPost(user, content);
        //assertEquals(user, userBean.getUser(username));
        assertEquals(user, post.getAuthor());
        assertEquals(content, post.getContent());
    }

    @Test
    public void registerComment() throws Exception {
        Post post = postBean.registerPost(user, content);
        Comment comment = postBean.registerComment(post, user, content);

        assertEquals(user, comment.getAuthor());
        assertEquals(content, comment.getContent());
        assertEquals(1, post.getComments().size());
        assertEquals(comment, post.getComments().get(0));
    }

    @Test
    public void getNumberOfPosts() throws Exception {
        Long posts = postBean.getNumberOfPosts();
        postBean.registerPost(user, content);
        assertEquals(posts + 1, postBean.getNumberOfPosts());
    }

}