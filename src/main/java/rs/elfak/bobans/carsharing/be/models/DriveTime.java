package rs.elfak.bobans.carsharing.be.models;

import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

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
public class DriveTime {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private boolean repeat;

    // MTWRFUS
    @Length(max = 7)
    private String repeatDays;

    @NotNull
    private DateTime date;

    @NotNull
    private DateTime departureTime;

    @NotNull
    private DateTime arrivalTime;

    public long getId() {
        return id;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public DateTime getDate() {
        return date;
    }

    public DateTime getDepartureTime() {
        return departureTime;
    }

    public DateTime getArrivalTime() {
        return arrivalTime;
    }

}
