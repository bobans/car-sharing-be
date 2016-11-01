package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.SharedDrive;

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

}
