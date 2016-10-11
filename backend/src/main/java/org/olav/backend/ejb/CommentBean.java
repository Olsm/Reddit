package org.olav.backend.ejb;

import org.olav.backend.entity.Comment;
import org.olav.backend.entity.Post;
import org.olav.backend.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CommentBean extends SubmissionBeanBase {

    public CommentBean(){}

    public Comment registerComment(Post post, User author, String content) {
        return super.registerComment(post, author, content);
    }

    public Comment getComment(Long id) {
        return em.find(Comment.class, id);
    }


}
