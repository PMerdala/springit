package pl.pmerdala.springit.model;

public class Comment {
    private final Long id;
    private final String comment;

    public Comment(Long id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }
}
