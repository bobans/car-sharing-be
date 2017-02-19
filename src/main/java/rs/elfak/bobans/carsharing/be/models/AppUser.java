package rs.elfak.bobans.carsharing.be.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
                        query = "SELECT u FROM AppUser u"
                ),
                @NamedQuery(
                        name = "User.findOther",
                        query = "SELECT u FROM AppUser u WHERE u.username <> :username"
                ),
                @NamedQuery(
                        name = "User.findByUsername",
                        query = "SELECT u FROM AppUser u WHERE u.username = :username"
                )
        }
)
public class AppUser {

    public static final int TYPE_PASSENGER = 1;
    public static final int TYPE_DRIVER = 2;

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String name;

    private String photoUrl;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    private DateTime birthDate;

    private DateTime driverLicenseDate;

    @NotNull
    private int userType;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Car> cars;

    @ElementCollection
    @CollectionTable(name = "UserPushIds", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "firebase_id")
    private List<String> firebaseIds;

    public AppUser() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public int getUserType() {
        return userType;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public List<String> getFirebaseIds() {
        return firebaseIds;
    }

    public void addFirebaseId(String firebaseId) {
        if (!firebaseIds.contains(firebaseId)) {
            firebaseIds.add(firebaseId);
        }
    }

    public void removeFirebaseId(String firebaseId) {
        firebaseIds.remove(firebaseId);
    }

    public void setFirebaseIds(List<String> firebaseIds) {
        this.firebaseIds = firebaseIds;
    }

}
