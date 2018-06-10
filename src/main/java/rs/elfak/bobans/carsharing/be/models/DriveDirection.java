package rs.elfak.bobans.carsharing.be.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Entity
public class DriveDirection {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String start;

    @NotNull
    private String stop;

    public long getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getStop() {
        return stop;
    }

}
