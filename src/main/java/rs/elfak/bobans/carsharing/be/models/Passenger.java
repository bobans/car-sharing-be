package rs.elfak.bobans.carsharing.be.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "Passenger.findAll",
                        query = "SELECT p FROM Passenger p ORDER BY p.user.id DESC"
                ),
                @NamedQuery(
                        name = "Passenger.findByUser",
                        query = "SELECT p FROM Passenger p WHERE p.user.id = :userId"
                )
        }
)
public class Passenger {

    public static final short STATUS_REQUESTED = 0;
    public static final short STATUS_ACCEPTED = 1;
    public static final short STATUS_REJECTED = -1;

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne(cascade = CascadeType.REMOVE)
    private AppUser user;

    private short status;

    public Passenger() {
    }

    public Passenger(AppUser user) {
        this.user = user;
        this.status = STATUS_REQUESTED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

}
