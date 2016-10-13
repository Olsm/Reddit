package org.olav.backend.ejb;

import org.olav.backend.entity.Comment;
import org.olav.backend.entity.ForumSubmission;
import org.olav.backend.entity.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class SubmissionBeanBase {

    @PersistenceContext
    protected EntityManager em;

    public ForumSubmission registerComment(ForumSubmission submission, User author, String content) {
        Comment comment = new Comment(author, content);
        em.persist(comment);
        submission.addComment(comment);
        em.merge(submission);
        return submission;
    }

    public ForumSubmission upvote(ForumSubmission submission) {
        submission.upVote();
        return submission;
    }

    public ForumSubmission downVote(ForumSubmission submission) {
        submission.downVote();
        return submission;
    }
}
