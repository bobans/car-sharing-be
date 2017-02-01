package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.Token;
import rs.elfak.bobans.carsharing.be.models.daos.CredentialsDAO;
import rs.elfak.bobans.carsharing.be.utils.ResponseMessage;

import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/register")
@Api(value = "/register", description = "User registration")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    private final CredentialsDAO dao;

    public RegisterResource(CredentialsDAO dao) {
        this.dao = dao;
    }

    @Timed
    @POST
    @UnitOfWork
    public Response register(@NotNull Credentials credentials) {
        if (dao.findByUsername(credentials.getUsername()) == null) {
            long id = dao.save(credentials);
            if (id != 0) {
                String token = credentials.getName() + ":" + credentials.getPassword();
                token = "Basic " + Base64.getEncoder().encodeToString(token.getBytes());
                return Response.created(null).entity(new Token(token)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Bad request")).build();
            }
        } else {
            return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(Response.Status.CONFLICT.getStatusCode(), "Username already exists")).build();
        }
    }

}
