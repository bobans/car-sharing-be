package rs.elfak.bobans.carsharing.be.models;

import org.hibernate.validator.constraints.NotEmpty;

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
                        name = "Car.findAll",
                        query = "SELECT c FROM Car c"
                ),
                @NamedQuery(
                        name = "Car.findByYear",
                        query = "SELECT c FROM Car c WHERE c.year = :year"
                ),
                @NamedQuery(
                        name = "Car.findByModel",
                        query = "SELECT c FROM Car c WHERE c.model.id = :modelId"
                ),
                @NamedQuery(
                        name = "Car.findByMake",
                        query = "SELECT c FROM Car c WHERE c.model.make.id = :makeId"
                )
        }
)
public class Car {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private Model model;

    private int year;

    @NotNull
    @NotEmpty
    private String registrationPlates;

    public Car() {
    }

    public long getId() {
        return id;
    }

    public Model getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getRegistrationPlates() {
        return registrationPlates;
    }

}
