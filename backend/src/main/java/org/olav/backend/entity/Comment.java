package org.olav.backend.entity;

import javax.persistence.*;

@Entity
public class Comment extends ForumSubmission {

    public Comment() {
        this(null, null);
    }

    public Comment(User author, String content) {
        super(author, content);
    }
}
