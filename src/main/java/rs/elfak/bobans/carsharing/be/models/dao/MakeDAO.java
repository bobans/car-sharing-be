package rs.elfak.bobans.carsharing.be.models.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.Make;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class MakeDAO extends AbstractDAO<Make> implements DAO<Make> {

    public MakeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Make> findAll() {
        return list(namedQuery("Make.findAll"));
    }

    @Override
    public Make findById(long id) {
        return get(id);
    }

    @Override
    public long save(Make obj) {
        return persist(obj).getId();
    }

}
