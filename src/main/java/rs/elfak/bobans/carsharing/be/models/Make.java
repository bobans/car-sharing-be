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
                        name = "Make.findAll",
                        query = "SELECT m FROM Make m"
                )
        }
)
public class Make {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotEmpty
    private String title;

    public Make() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
