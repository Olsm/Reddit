package org.olav.backend.ejb;

import org.olav.backend.entity.Comment;
import org.olav.backend.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CommentBean {

    @PersistenceContext
    protected EntityManager em;

    public CommentBean(){}

    public Comment registerComment(Comment parentComment, User author, String content) {
        Comment comment = new Comment(author, content);
        em.persist(comment);
        parentComment.addComment(comment);
        em.merge(parentComment);
        return comment;
    }

    public Comment getComment(Long id) {
        return em.find(Comment.class, id);
    }


}
