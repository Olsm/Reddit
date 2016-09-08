import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id @GeneratedValue
    private Integer id;
    private String author;
    private String content;
    private Date date;
    private int upVotes;
    private int downVotes;
    //TODO: add comments

    public Post() {
        this(null, null);
    }

    public Post(String author, String content) {
        this.author = author;
        this.content = content;
        this.date = new Date();
        this.upVotes = 0;
        this.downVotes = 0;
    }

    public Integer getId() {
        return id;
    }

    public String getAuthor() {
        return author;
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

    public void upVote() {
        this.upVotes++;
    }

    public void downVote() {
        this.downVotes++;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
