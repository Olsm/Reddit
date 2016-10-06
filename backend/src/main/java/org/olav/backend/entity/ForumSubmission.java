package org.olav.backend.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.olav.backend.entity.Comment;
import org.olav.backend.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@MappedSuperclass
public class ForumSubmission {
    @Id @GeneratedValue
    private Long id;
    @NotNull @ManyToOne
    private User author;
    @NotEmpty @Column(length = 50000)
    private String content;
    @Past
    private Date date;
    private int upVotes;
    private int downVotes;
    @OneToMany
    private List<Comment> comments;

    public ForumSubmission() {
        this(null, null);
    }

    public ForumSubmission(User author, String content) {
        this.author = author;
        this.content = content;
        this.date = new Date();
        this.upVotes = 0;
        this.downVotes = 0;
        comments = new ArrayList<Comment>();
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
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
}
