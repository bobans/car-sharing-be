package rs.elfak.bobans.carsharing.be.utils;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.User;
import rs.elfak.bobans.carsharing.be.models.dao.UserDAO;

import java.util.Optional;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDAO dao;

    public SimpleAuthenticator(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    @UnitOfWork
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        User user = dao.findByUsername(credentials.getUsername());
        if (user != null && user.getPassword().compareTo(credentials.getPassword()) == 0) {
            return Optional.of(dao.findByUsername(credentials.getUsername()));
        }
        return Optional.empty();
    }

}
