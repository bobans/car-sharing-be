package rs.elfak.bobans.carsharing.be.models;

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
                        name = "User.findAll",
                        query = "SELECT u FROM User u"
                ),
                @NamedQuery(
                        name = "User.findByUsername",
                        query = "SELECT u FROM User u WHERE u.username = :username"
                )
        }
)
public class User implements Principal {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
