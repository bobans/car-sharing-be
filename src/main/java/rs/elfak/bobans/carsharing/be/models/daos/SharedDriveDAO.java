package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.SharedDrive;

import java.util.ArrayList;
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

    public List<SharedDrive> findByUser(String username, int offset, int limit) {
        return list(namedQuery("SharedDrive.findByUser").setParameter("username", username).setFirstResult(offset).setMaxResults(limit));
    }

    public List<SharedDrive> filterDrives(String departure, String destination, int offset, int limit) {
        List<SharedDrive> drives = list(namedQuery("SharedDrive.filter")
                .setParameter("departure", departure)
                .setParameter("destination", destination)
                .setFirstResult(offset)
                .setMaxResults(limit));
        List<SharedDrive> result = new ArrayList<>();
        for (SharedDrive drive : drives) {
            List<String> stops = new ArrayList<>();
            stops.add(drive.getDeparture());
            if (drive.getStops() != null) {
                stops.addAll(drive.getStops());
            }
            stops.add(drive.getDestination());
            if (stops.indexOf(departure) < stops.indexOf(destination)) {
                result.add(drive);
            }
        }
        return result;
    }

}
