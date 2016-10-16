package rs.elfak.bobans.carsharing.be.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "User.findAll",
                        query = "SELECT u FROM User u"
                ),
                @NamedQuery(
                        name = "User.findByUsername",
                        query = "SELECT u FROM User u WHERE u.credentials.username = :username"
                )
        }
)
public class User {

    public static final int TYPE_PASSENGER = 1;
    public static final int TYPE_DRIVER = 2;

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Credentials credentials;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    private DateTime birthDate;

    @NotNull
    private DateTime driverLicenseDate;

    @NotNull
    private int userType;

    @Null
    @OneToMany
    private List<Car> cars;

    public User() {
    }

    public long getId() {
        return id;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public DateTime getBirthDate() {
        return birthDate;
    }

    public DateTime getDriverLicenseDate() {
        return driverLicenseDate;
    }

    public List<Car> getCars() {
        return cars;
    }

    public int getUserType() {
        return userType;
    }

}
