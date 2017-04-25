package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.AppUser;
import rs.elfak.bobans.carsharing.be.models.Credentials;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class UserDAO extends AbstractDAO<AppUser> implements DAO<AppUser> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<AppUser> findAll() {
        return list(namedQuery("User.findAll"));
    }

    public List<AppUser> findAllExceptMe(String username) {
        return list(namedQuery("User.findOther").setParameter("username", username));
    }

    @Override
    public AppUser findById(long id) {
        return get(id);
    }

    @Override
    public long save(AppUser obj) {
        return persist(obj).getId();
    }

    public AppUser findByUsername(String username) {
        return uniqueResult(namedQuery("User.findByUsername").setParameter("username", username));
    }

    public void delete(AppUser user) {
        currentSession().delete(user);
    }

}
