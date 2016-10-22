package rs.elfak.bobans.carsharing.be.utils;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.daos.CredentialsDAO;

import java.util.Optional;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, Credentials> {

    private CredentialsDAO dao;

    public SimpleAuthenticator(CredentialsDAO dao) {
        this.dao = dao;
    }

    @Override
    @UnitOfWork
    public Optional<Credentials> authenticate(BasicCredentials credentials) throws AuthenticationException {
        Credentials found = dao.findByUsername(credentials.getUsername());
        if (found != null && found.getPassword().equals(credentials.getPassword())) {
            return Optional.of(found);
        }
        return Optional.empty();
    }

}
