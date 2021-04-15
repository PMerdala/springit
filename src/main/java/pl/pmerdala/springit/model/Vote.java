package pl.pmerdala.springit.model;

public class Vote {
    private final Long id;
    //private LinkSchema link;
    private final int vote;

    public Vote(Long id, int vote) {
        this.id = id;
        this.vote = vote;
    }

    public Long getId() {
        return id;
    }

    public int getVote() {
        return vote;
    }
}
