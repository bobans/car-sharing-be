package rs.elfak.bobans.carsharing.be.models.daos;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import rs.elfak.bobans.carsharing.be.models.firebase.FirebaseToken;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class FirebaseTokenDAO extends AbstractDAO<FirebaseToken> {

    public FirebaseTokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public String save(FirebaseToken firebaseToken) {
        return persist(firebaseToken).getDeviceId();
    }

    public void remove(String deviceId) {
        FirebaseToken token = get(deviceId);
        if (token != null) {
            currentSession().delete(get(deviceId));
        }
    }

    public FirebaseToken findById(String deviceId) {
        return get(deviceId);
    }

}
