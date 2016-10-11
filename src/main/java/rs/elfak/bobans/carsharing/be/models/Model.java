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
                        name = "Model.findAll",
                        query = "SELECT m FROM Model m"
                ),
                @NamedQuery(
                        name = "Model.findByMake",
                        query = "SELECT m FROM Model m WHERE m.make.id = :makeId"
                )
        }
)
public class Model {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private Make make;

    @NotNull
    @NotEmpty
    private String title;

    public Model() {
    }

    public long getId() {
        return id;
    }

    public Make getMake() {
        return make;
    }

    public String getTitle() {
        return title;
    }

}
