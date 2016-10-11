package rs.elfak.bobans.carsharing.be.models.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.Car;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class CarDAO extends AbstractDAO<Car> implements DAO<Car> {

    public CarDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Car> findAll() {
        return list(namedQuery("Car.findAll"));
    }

    @Override
    public Car findById(long id) {
        return get(id);
    }

    @Override
    public long save(Car obj) {
        return persist(obj).getId();
    }

    public List<Car> findByYear(int year) {
        return list(namedQuery("Car.findByYear").setParameter("year", year));
    }

    public List<Car> findByMake(long makeId) {
        return list(namedQuery("Car.findByMake").setParameter("makeId", makeId));
    }

    public List<Car> findByModel(long modelId) {
        return list(namedQuery("Car.findByModel").setParameter("modelId", modelId));
    }
}
