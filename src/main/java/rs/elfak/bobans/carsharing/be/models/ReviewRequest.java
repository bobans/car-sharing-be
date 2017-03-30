package rs.elfak.bobans.carsharing.be.models;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class ReviewRequest {

    private int score;
    private String comment;

    public ReviewRequest() {
    }

    public int getScore() {
        if (score > 5) {
            score = 5;
        }
        if (score < 1) {
            score = 1;
        }
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
