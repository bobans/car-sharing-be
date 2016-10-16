package rs.elfak.bobans.carsharing.be.models.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.Model;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class ModelDAO extends AbstractDAO<Model> implements DAO<Model> {

    public ModelDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Model> findAll() {
        return list(namedQuery("Model.findAll"));
    }

    @Override
    public Model findById(long id) {
        return get(id);
    }

    @Override
    public long save(Model obj) {
        return persist(obj).getId();
    }

    public List<Model> findByMake(long makeId) {
        return list(namedQuery("Model.findByMake").setParameter("makeId", makeId));
    }

    public Model findByMakeAndModel(long makeId, long modelId) {
        return uniqueResult(namedQuery("Model.findByMakeAndModel").setParameter("makeId", makeId).setParameter("modelId", modelId));
    }

}
