package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import rs.elfak.bobans.carsharing.be.models.SharedDrive;
import rs.elfak.bobans.carsharing.be.utils.Constants;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class SharedDriveDAO extends AbstractDAO<SharedDrive> implements DAO<SharedDrive> {

    public SharedDriveDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<SharedDrive> findAll() {
        return list(namedQuery("SharedDrive.findAll"));
    }

    @Override
    public SharedDrive findById(long id) {
        return get(id);
    }

    public void delete(SharedDrive drive) {
        currentSession().delete(drive);
    }

    @Override
    public long save(SharedDrive obj) {
        return persist(obj).getId();
    }

    public List<SharedDrive> findPaged(int offset, int limit) {
        return list(namedQuery("SharedDrive.findAll").setFirstResult(offset).setMaxResults(limit));
    }

    public List<SharedDrive> findByUser(String username) {
        return list(namedQuery("SharedDrive.findByUser").setParameter("username", username));
    }

    public List<SharedDrive> filterDrives(DateTime date, String repeatDays, int offset, int limit) {
        String days = "";
        boolean lastQ = false;
        for (int i=0; i< Constants.DAYS_IN_WEEK.length; i++) {
            if (repeatDays.contains(Constants.DAYS_IN_WEEK[i])) {
                days += Constants.DAYS_IN_WEEK[i];
                lastQ = false;
            } else if (!lastQ) {
                days += "%";
                lastQ = true;
            }
        }
        return list(namedQuery("SharedDrive.filter")
                .setParameter("date", date)
                .setParameter("days", days)
                .setFirstResult(offset)
                .setMaxResults(limit));
    }

}
