import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NamedQueries(
        @NamedQuery(name = Post.SUM_POSTS, query = "select sum(p) from Post p"),
        @NamedQuery(name = Post.SUM_POSTS_IN_NORWAY, query = "select sum(p) from Post u where u.address.country = 'Norway'")
)

@Entity
public class Post {

    public static final String SUM_POSTS = "SUM_POSTS";
    public static final String SUM_POSTS_IN_NORWAY = "SUM_POSTS_IN_NORWAY";

    @Id @GeneratedValue
    private Long id;
    private String author;
    private String content;
    private Date date;
    private int upVotes;
    private int downVotes;
    @OneToMany
    private List<Comment> comments;

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

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public List<Comment> getComments() {
        if(comments != null) return comments;
        return new ArrayList<Comment>();
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
}
