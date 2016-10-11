package org.olav.backend.ejb;

import org.olav.backend.entity.Comment;
import org.olav.backend.entity.Post;
import org.olav.backend.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PostBean {

    @PersistenceContext
    protected EntityManager em;

    public PostBean(){}

    public Post registerPost(User author, String content) {
        Post post = new Post(author, content);
        em.persist(post);
        return post;
    }

    public Comment registerComment(Post parentPost, User author, String content) {
        Comment comment = new Comment(author, content);
        em.persist(comment);
        parentPost.addComment(comment);
        em.merge(parentPost);
        return comment;
    }

    public Post getPost(Long id) {
        return em.find(Post.class, id);
    }

    public long getNumberOfPosts(){
        Query query = em.createNamedQuery(Post.SUM_POSTS);
        return ((Number)query.getSingleResult()).intValue();
    }

}