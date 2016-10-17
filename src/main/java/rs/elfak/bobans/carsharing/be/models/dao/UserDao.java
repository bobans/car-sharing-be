package rs.elfak.bobans.carsharing.be.models.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.User;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class UserDAO extends AbstractDAO<User> implements DAO<User> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<User> findAll() {
        return list(namedQuery("User.findAll"));
    }

    public List<User> findAllExceptMe(String username) {
        return list(namedQuery("User.findAllExceptMe").setParameter("username", username));
    }

    @Override
    public User findById(long id) {
        return get(id);
    }

    @Override
    public long save(User obj) {
        return persist(obj).getId();
    }

    public User findByUsername(String username) {
        return uniqueResult(namedQuery("User.findByUsername").setParameter("username", username));
    }

}
