package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.Passenger;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class PassengerDAO extends AbstractDAO<Passenger> implements DAO<Passenger> {

    public PassengerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Passenger> findAll() {
        return list(namedQuery("Passenger.findAll"));
    }

    @Override
    public Passenger findById(long id) {
        return get(id);
    }

    public Passenger findByUserIAndDriveId(long userId) {
        return uniqueResult(namedQuery("Passenger.findByUser").setParameter("userId", userId));
    }

    @Override
    public long save(Passenger obj) {
        return persist(obj).getId();
    }

}
