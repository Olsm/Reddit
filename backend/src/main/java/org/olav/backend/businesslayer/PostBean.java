package org.olav.backend.businesslayer;
import org.olav.backend.datalayer.Post;
import org.olav.backend.datalayer.User;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class PostBean extends SubmissionBeanBase {

    public PostBean(){}

    public Post registerPost(User author, String title, String content) {
        Post post = new Post(author, title, content);
        em.persist(post);
        return post;
    }

    public Post registerComment(Post post, User author, String content) {
        return (Post) super.registerComment(post, author, content);
    }

    public Post getPost(Long id) {
        return em.find(Post.class, id);
    }

    public int getNumberOfPosts(){
        Query query = em.createNamedQuery(Post.SUM_POSTS);
        return ((Number)query.getSingleResult()).intValue();
    }

    public List<Post> getAll() {
        Query query = em.createQuery("SELECT p FROM Post p");
        return (List<Post>) query.getResultList();
    }
}
