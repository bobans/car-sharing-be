package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.Credentials;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class CredentialsDAO extends AbstractDAO<Credentials> implements DAO<Credentials> {

    public CredentialsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Credentials> findAll() {
        return list(namedQuery("Credentials.findAll"));
    }

    @Override
    public Credentials findById(long id) {
        return get(id);
    }

    @Override
    public long save(Credentials obj) {
        return persist(obj).getId();
    }

    public Credentials findByUsername(String username) {
        return uniqueResult(namedQuery("Credentials.findByUsername").setParameter("username", username));
    }

}
