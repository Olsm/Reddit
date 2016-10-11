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

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class CommentBeanTest {

    @Deployment
    public static JavaArchive createDeployment() {

        return ShrinkWrap.create(JavaArchive.class)
                .addPackages(true, "org.olav.backend.entity", "org.olav.backend.ejb")
                .addAsResource("META-INF/persistence.xml");
    }

    @EJB
    private CommentBean commentBean;
    @EJB
    private UserBean userBean;
    @EJB
    private PostBean postBean;

    private User user;
    private Post post;
    private String content;
    private static int counter;

    @Before
    public void setupBefore() {
        user = userBean.registerNewUser("username" + counter++, "such@mail.com", "Shiba Inu", null);
        content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus volutpat turpis vitae bibendum auctor. Aliquam posuere tempus hendrerit. Sed at leo massa. Aenean eget libero est. Cras semper neque vitae nulla interdum rutrum. Duis est augue, vestibulum et justo eget, commodo consequat nulla. Sed elit libero, tincidunt eget finibus quis, cursus non ex. Nam in luctus ante. Quisque odio orci, scelerisque vel fringilla eget, suscipit ut sapien. Vivamus elementum eros vitae risus imperdiet aliquet. In ac dui sem. Morbi quis eros eleifend, feugiat tellus sed, malesuada massa.";
        post = postBean.registerPost(user, content);
    }

    @Test
    public void registercomment() throws Exception {
        Comment parentComment = postBean.registerComment(post, user, content);
        Long commentId = commentBean.registerComment(parentComment, user, content).getId();
        Comment comment = commentBean.getComment(commentId);

        assertEquals(user, comment.getAuthor());
        assertEquals(content, comment.getContent());
        assertEquals(1, parentComment.getComments().size());
        assertEquals(comment, parentComment.getComments().get(0));
    }

}