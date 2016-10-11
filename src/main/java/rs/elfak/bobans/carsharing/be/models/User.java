package rs.elfak.bobans.carsharing.be.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.Principal;

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

    private Car car;

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

    public Car getCar() {
        return car;
    }
}
