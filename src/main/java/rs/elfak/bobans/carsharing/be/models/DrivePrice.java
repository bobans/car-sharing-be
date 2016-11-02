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
public class DrivePrice {

    public static final int PRICE_TOTAL = 1;
    public static final int PRICE_PER_PASSENGER = 2;

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private int type;

    @NotNull
    private double price;

    public long getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

}
