package rs.elfak.bobans.carsharing.be.models;

import org.hibernate.validator.constraints.NotEmpty;

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
                        name = "SharedDrive.findAll",
                        query = "SELECT sd FROM SharedDrive sd ORDER BY sd.id DESC"
                ),
                @NamedQuery(
                        name = "SharedDrive.findByUser",
                        query = "SELECT sd FROM SharedDrive sd WHERE sd.user.username=:username ORDER BY sd.id DESC"
                ),
                @NamedQuery(
                        name = "SharedDrive.filter",
                        query = "SELECT DISTINCT sd FROM SharedDrive sd LEFT JOIN sd.stops s WHERE (sd.departure LIKE :departure OR s LIKE :departure) AND (s LIKE :destination OR sd.destination LIKE :destination)"
                )
        }
)
public class SharedDrive {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    @NotEmpty
    private String departure;

    @NotNull
    @NotEmpty
    private String destination;

    @ElementCollection
    @CollectionTable(name = "DriveStops", joinColumns = @JoinColumn(name = "drive_id"))
    @Column(name = "stop")
    private List<String> stops;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private DrivePreferences preferences;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private DriveTime time;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private DrivePrice price;

    @NotNull
    private int seats;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<Passenger> passengers;

    public long getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getDeparture() {
        return departure;
    }

    public String getDestination() {
        return destination;
    }

    public List<String> getStops() {
        return stops;
    }

    public DrivePreferences getPreferences() {
        return preferences;
    }

    public DriveTime getTime() {
        return time;
    }

    public DrivePrice getPrice() {
        return price;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

}
