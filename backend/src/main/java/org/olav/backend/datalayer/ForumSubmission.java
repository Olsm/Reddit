package org.olav.backend.datalayer;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@MappedSuperclass
public class ForumSubmission {
    @Id @GeneratedValue
    private Long id;
    @NotNull @ManyToOne
    private User author;
    @Max(100)
    private String title;
    @NotEmpty @Column(length = 50000)
    private String content;
    @Past
    private Date date;
    private int upVotes;
    private int downVotes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    public ForumSubmission() {
        this(null, null, null);
    }

    public ForumSubmission(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.date = new Date();
        this.upVotes = 0;
        this.downVotes = 0;
        comments = Collections.synchronizedList(new ArrayList<Comment>());
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public synchronized List<Comment> getComments() {
        return comments;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public int getVotes() {
        return upVotes - downVotes;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public synchronized void addComment(Comment comment) {comments.add(comment);}

    public void upVote() {
        this.upVotes++;
    }

    public void downVote() {
        this.downVotes++;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!ForumSubmission.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final ForumSubmission other = (ForumSubmission) obj;
        if ((this.getId() == null) ? (other.getId() != null) : !this.getId().equals(other.getId())) {
            return false;
        }
        return true;
    }
}
