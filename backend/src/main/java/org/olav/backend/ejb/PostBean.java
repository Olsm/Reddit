package org.olav.backend.ejb;
import org.olav.backend.entity.Comment;
import org.olav.backend.entity.Post;
import org.olav.backend.entity.User;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class PostBean extends SubmissionBeanBase {

    public PostBean(){}

    public Post registerPost(User author, String content) {
        Post post = new Post(author, content);
        em.persist(post);
        return post;
    }

    public Post registerComment(Post post, User author, String content) {
        return getPost(super.registerComment(post, author, content).getId());
    }

    public Post getPost(Long id) {
        return em.find(Post.class, id);
    }

    public long getNumberOfPosts(){
        Query query = em.createNamedQuery(Post.SUM_POSTS);
        return ((Number)query.getSingleResult()).intValue();
    }

}
