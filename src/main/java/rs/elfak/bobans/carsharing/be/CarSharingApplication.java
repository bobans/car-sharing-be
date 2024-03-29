package rs.elfak.bobans.carsharing.be;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.exceptionMappers.RelatedEntityMissingExceptionMapper;
import rs.elfak.bobans.carsharing.be.models.*;
import rs.elfak.bobans.carsharing.be.models.daos.*;
import rs.elfak.bobans.carsharing.be.models.firebase.FirebaseToken;
import rs.elfak.bobans.carsharing.be.resources.*;
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
            AppUser.class,
            Car.class,
            Make.class,
            Model.class,
            SharedDrive.class,
            DrivePreferences.class,
            DriveTime.class,
            DrivePrice.class,
            Passenger.class,
            FirebaseToken.class,
            UserReview.class,
            DriveDirection.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(CarSharingConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final SwaggerBundle<CarSharingConfiguration> swagger = new SwaggerBundle<CarSharingConfiguration>() {
        @Override
        protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(CarSharingConfiguration carSharingConfiguration) {
            return carSharingConfiguration.getSwagger();
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
        bootstrap.addBundle(swagger);
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
        SharedDriveDAO driveDAO = new SharedDriveDAO(sessionFactory);
        PassengerDAO passengerDAO = new PassengerDAO(sessionFactory);
        FirebaseTokenDAO firebaseTokenDAO = new FirebaseTokenDAO(sessionFactory);
        UserReviewDAO userReviewDAO = new UserReviewDAO(sessionFactory);

        SimpleAuthenticator authenticator = new UnitOfWorkAwareProxyFactory(hibernate)
                .create(SimpleAuthenticator.class, CredentialsDAO.class, credentialsDAO);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<Credentials>()
                    .setAuthenticator(authenticator)
                    .setRealm("CarSharingRealm")
                    .setUnauthorizedHandler(new CarSharingUnauthorizedHandler())
                    .setPrefix("Basic")
                    .buildAuthFilter()));
        environment.jersey().register(new LoginResource());
        environment.jersey().register(new RegisterResource(credentialsDAO));
        environment.jersey().register(new UserResource(userDAO, credentialsDAO, userReviewDAO, passengerDAO, driveDAO));
        environment.jersey().register(new CarResource(carDAO, userDAO));
        environment.jersey().register(new MakeModelResource(makeDAO, modelDAO));
        environment.jersey().register(new SharedDriveResource(driveDAO, userDAO, passengerDAO, userReviewDAO));
        environment.jersey().register(new FCMResource(userDAO, firebaseTokenDAO));
        environment.jersey().register(MultiPartFeature.class);
        environment.jersey().register(new UserReviewResource(userDAO, userReviewDAO, driveDAO));
        environment.jersey().register(new ResetPasswordResource(userDAO, credentialsDAO));
    }
}
