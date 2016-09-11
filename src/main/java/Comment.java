import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;
    private String author;
    private String content;
    private Date date;
    private int upVotes;
    private int downVotes;
    @OneToMany
    private List<Comment> comments;

    public Comment() {
        this(null, null);
    }

    public Comment(String author, String content) {
        this.author = author;
        this.content = content;
        this.date = new Date();
        this.upVotes = 0;
        this.downVotes = 0;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public List<Comment> getComments() {
        if(comments != null) return comments;
        return new ArrayList<Comment>();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getVotes() {
        return upVotes - downVotes;

    }

    public void upVote() {
        this.upVotes++;
    }

    public void downVote() {
        this.downVotes++;
    }
}
