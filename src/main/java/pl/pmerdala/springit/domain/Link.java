package pl.pmerdala.springit.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Link extends Auditable{
    private static final String HOST_NAME_PREFIX = "www.";
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String title;
    @NonNull
    private String url;

    private String description;

    @OneToMany(mappedBy = "link")
    private List<Comment> comments = new ArrayList<>();

    @Setter(AccessLevel.PRIVATE)
    private int voteCount=0;

    public Link(@NonNull String title, @NonNull String url, String description) {
        this.title = title;
        this.url = url;
        this.description = description;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public String getDomainName() throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith(HOST_NAME_PREFIX) ? domain.substring(4) : domain;
    }

    public void addVote(Vote vote) {
        voteCount+=vote.getDirection();
    }

    public void removeVote(Vote vote){
        voteCount-=vote.getDirection();
    }

}
