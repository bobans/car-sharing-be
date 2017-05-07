package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.UserReview;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class UserReviewDAO extends AbstractDAO<UserReview> implements DAO<UserReview> {

    public UserReviewDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<UserReview> findAll() {
        return list(namedQuery("UserReview.findAll"));
    }

    public List<UserReview> findForUser(String username) {
        return list(namedQuery("UserReview.findForUser").setParameter("username", username));
    }

    public UserReview find(String username, String reviewer, long sharedDriveId) {
        return uniqueResult(namedQuery("UserReview.find")
                .setParameter("username", username)
                .setParameter("reviewer", reviewer)
                .setParameter("sharedDrive", sharedDriveId));
    }

    @Override
    public UserReview findById(long id) {
        return get(id);
    }

    @Override
    public long save(UserReview obj) {
        return persist(obj).getId();
    }

    public List<UserReview> findForDrive(long driveId) {
        return list(namedQuery("UserReview.findForDrive").setParameter("driveId", driveId));
    }

}
