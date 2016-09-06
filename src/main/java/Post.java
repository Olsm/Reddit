import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Post {
    @Embedded
    DBHelper dbHelper;
    @Id @GeneratedValue
    private int id;
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

        dbHelper = new DBHelper();
        dbHelper.persistInATransaction(this);
    }

    public int getId() {
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

    public boolean setContent(String content) {
        this.content = content;
        return dbHelper.persistInATransaction(this);
    }

    public boolean upVote() {
        this.upVotes++;
        return dbHelper.persistInATransaction(this);
    }

    public boolean downVote() {
        this.downVotes++;
        return dbHelper.persistInATransaction(this);
    }
}
