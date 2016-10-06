import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Post.SUM_POSTS, query = "select count(p) from Post p"),
        @NamedQuery(name = Post.SUM_POSTS_IN_NORWAY, query = "select count(p) from Post p where p.author.address.country = 'Norway'")
})

@Entity
public class Post extends ForumSubmission {

    public static final String SUM_POSTS = "SUM_POSTS";
    public static final String SUM_POSTS_IN_NORWAY = "SUM_POSTS_IN_NORWAY";

    public Post() {this(null, null);}

    public Post(User author, String content) {
        super(author, content);
    }

}
