package rs.elfak.bobans.carsharing.be.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "UserReview.findAll",
                        query = "SELECT ur FROM UserReview ur"
                ),
                @NamedQuery(
                        name = "UserReview.findForUser",
                        query = "SELECT ur FROM UserReview ur WHERE ur.user.username = :username"
                ),
                @NamedQuery(
                        name = "UserReview.findForReviewer",
                        query = "SELECT ur FROM UserReview ur WHERE ur.reviewer.username = :username"
                ),
                @NamedQuery(
                        name = "UserReview.find",
                        query = "SELECT ur FROM UserReview ur WHERE ur.user.username = :username AND ur.reviewer.username=:reviewer AND ur.sharedDrive.id=:sharedDrive"
                ),
                @NamedQuery(
                        name = "UserReview.findForDrive",
                        query = "SELECT ur FROM UserReview ur WHERE ur.sharedDrive.id = :driveId"
                )
        }
)
public class UserReview {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private int userType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @NotNull
    private int reviewerType;

    @ManyToOne
    private AppUser reviewer;

    @NotNull
    private int score;

    @NotNull
    @NotEmpty
    private String comment;

    @ManyToOne
    @JoinColumn(name = "drive_id")
    private SharedDrive sharedDrive;

    public UserReview() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public int getReviewerType() {
        return reviewerType;
    }

    public void setReviewerType(int reviewerType) {
        this.reviewerType = reviewerType;
    }

    public AppUser getReviewer() {
        return reviewer;
    }

    public void setReviewer(AppUser reviewer) {
        this.reviewer = reviewer;
    }

    public int getScore() {
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

    public SharedDrive getSharedDrive() {
        return sharedDrive;
    }

    public void setSharedDrive(SharedDrive sharedDrive) {
        this.sharedDrive = sharedDrive;
    }

}
