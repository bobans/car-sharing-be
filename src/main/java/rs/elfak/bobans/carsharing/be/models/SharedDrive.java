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
                )
        }
)
public class SharedDrive {

    public static final int FLAG_NEGATIVE = -1;
    public static final int FLAG_NEUTRAL = 0;
    public static final int FLAG_POSITIVE = 1;

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
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
    private int music;

    @NotNull
    private int pets;

    @NotNull
    private int smoking;

    @NotNull
    private int talk;

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
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

    public int getMusic() {
        return music;
    }

    public int getPets() {
        return pets;
    }

    public int getSmoking() {
        return smoking;
    }

    public int getTalk() {
        return talk;
    }

}
