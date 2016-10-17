package org.olav.backend.datalayer;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Comment.SUM_COMMENTS, query = "select count(c) from Comment c"),
})

@Entity
public class Comment extends ForumSubmission {

    public static final String SUM_COMMENTS = "SUM_COMMENTS";

    public Comment() {
        this(null, null);
    }

    public Comment(User author, String content) {
        super(author, null, content);
    }
}
