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
public class DrivePreferences {

    public static final int FLAG_NEGATIVE = -1;
    public static final int FLAG_NEUTRAL = 0;
    public static final int FLAG_POSITIVE = 1;

    @Id
    @GeneratedValue
    private long id;

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
