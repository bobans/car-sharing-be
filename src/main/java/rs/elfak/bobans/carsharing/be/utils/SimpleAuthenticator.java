package rs.elfak.bobans.carsharing.be.utils;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import rs.elfak.bobans.carsharing.be.models.Credentials;

import java.util.Optional;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class SimpleAuthenticator implements Authenticator<BasicCredentials, Credentials> {

    @Override
    public Optional<Credentials> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new Credentials(credentials.getUsername()));
        }
        return Optional.empty();
    }

}
