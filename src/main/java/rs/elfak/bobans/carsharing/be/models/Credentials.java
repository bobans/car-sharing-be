package rs.elfak.bobans.carsharing.be.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.Principal;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "Credentials.findAll",
                        query = "SELECT c FROM Credentials c"
                ),
                @NamedQuery(
                        name = "Credentials.findByUsername",
                        query = "SELECT c FROM Credentials c WHERE c.username = :username"
                )
        }
)
public class Credentials implements Principal {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    @Length(min = 6)
    private String username;

    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String password;

    @Override
    @JsonIgnore
    public String getName() {
        return username;
    }

    public Credentials() {
    }

    public Credentials(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
