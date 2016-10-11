package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.dao.CredentialsDAO;
import rs.elfak.bobans.carsharing.be.utils.ResponseMessage;

import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    private final CredentialsDAO dao;

    public RegisterResource(CredentialsDAO dao) {
        this.dao = dao;
    }

    @Timed
    @POST
    @UnitOfWork
    public Response login(@NotNull Credentials credentials) {
        if (dao.findByUsername(credentials.getUsername()) == null) {
            long id = dao.save(credentials);
            if (id != 0) {
                return Response.created(null).build();
            }
        } else {
            return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(409, "Username already exists")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(400, "Bad request")).build();
    }

}
