package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.AppUser;

import java.util.ArrayList;
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

    public AppUser findByEmail(String email) {
        return uniqueResult(namedQuery("User.findByEmail").setParameter("email", email));
    }

    public List<AppUser> findByStops(List<String> stops, String currentUser) {
        List<AppUser> users = list(namedQuery("User.findByStops").setParameterList("stops", stops).setParameter("username", currentUser));
        List<AppUser> result = new ArrayList<>();
        for (AppUser user : users) {
            if (stops.indexOf(user.getStoredDirection().getStart()) < stops.indexOf(user.getStoredDirection().getStop())) {
                result.add(user);
            }
        }
        return result;
    }

    public void delete(AppUser user) {
        currentSession().delete(user);
    }

}
