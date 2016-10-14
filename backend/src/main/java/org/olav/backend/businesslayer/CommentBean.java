package org.olav.backend.businesslayer;

import org.olav.backend.datalayer.Comment;
import org.olav.backend.datalayer.Post;
import org.olav.backend.datalayer.User;

import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
public class CommentBean extends SubmissionBeanBase {

    public CommentBean(){}

    public Comment registerComment(Post post, User author, String content) {
        return (Comment) super.registerComment(post, author, content);
    }

    public Comment getComment(Long id) {
        return em.find(Comment.class, id);
    }


    public int getNumberOfComments() {
        Query query = em.createNamedQuery(Comment.SUM_COMMENTS);
        return ((Number)query.getSingleResult()).intValue();
    }
}
