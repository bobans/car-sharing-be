package rs.elfak.bobans.carsharing.be;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.exceptionMappers.RelatedEntityMissingExceptionMapper;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.Make;
import rs.elfak.bobans.carsharing.be.models.Model;
import rs.elfak.bobans.carsharing.be.models.User;
import rs.elfak.bobans.carsharing.be.models.dao.*;
import rs.elfak.bobans.carsharing.be.resources.LoginResource;
import rs.elfak.bobans.carsharing.be.resources.RegisterResource;
import rs.elfak.bobans.carsharing.be.resources.UserResource;
import rs.elfak.bobans.carsharing.be.utils.CarSharingUnauthorizedHandler;
import rs.elfak.bobans.carsharing.be.utils.SimpleAuthenticator;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class CarSharingApplication extends Application<CarSharingConfiguration> {

    private final HibernateBundle<CarSharingConfiguration> hibernate = new HibernateBundle<CarSharingConfiguration>(
            Credentials.class,
            User.class,
            Make.class,
            Model.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(CarSharingConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new CarSharingApplication().run(args);
    }

    @Override
    public void run(CarSharingConfiguration carSharingConfiguration, Environment environment) throws Exception {
        registerExceptionMappers(environment);
        registerHibernateResources(environment);
    }

    @Override
    public String getName() {
        return "Car Sharing";
    }

    @Override
    public void initialize(Bootstrap<CarSharingConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
        super.initialize(bootstrap);
    }

    private void registerExceptionMappers(Environment environment) {
        environment.jersey().register(new RelatedEntityMissingExceptionMapper(environment.metrics()));
    }

    private void registerHibernateResources(Environment environment) {
        SessionFactory sessionFactory = hibernate.getSessionFactory();
        CredentialsDAO credentialsDAO = new CredentialsDAO(sessionFactory);
        UserDAO userDAO = new UserDAO(sessionFactory);
        CarDAO carDAO = new CarDAO(sessionFactory);
        MakeDAO makeDAO = new MakeDAO(sessionFactory);
        ModelDAO modelDAO = new ModelDAO(sessionFactory);
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<Credentials>()
                    .setAuthenticator(new SimpleAuthenticator())
                    .setRealm("CarSharingSuperSecret")
                    .setUnauthorizedHandler(new CarSharingUnauthorizedHandler())
                    .setPrefix("Basic")
                    .buildAuthFilter()));
        environment.jersey().register(new LoginResource());
        environment.jersey().register(new RegisterResource(credentialsDAO));
        environment.jersey().register(new UserResource(userDAO));
        // TODO
//        StationDAO stationDao = new StationDAO(sessionFactory);
//        LineDAO lineDAO = new LineDAO(sessionFactory, stationDao);
//        TrainDAO trainDAO = new TrainDAO(sessionFactory, lineDAO);
//        ReservationsDAO reservationsDAO = new ReservationsDAO(sessionFactory);
//        environment.jersey().register(new StationResource(stationDao));
//        environment.jersey().register(new LineResource(lineDAO));
//        environment.jersey().register(new TrainResource(new TimeTableManipulator(trainDAO, lineDAO, reservationsDAO)));
//        environment.jersey().register(new ReservationsResource(reservationsDAO));
//        environment.jersey().register(ParamConverterProviderImpl.class);
    }
}
