package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.User;
import rs.elfak.bobans.carsharing.be.models.dao.UserDAO;
import rs.elfak.bobans.carsharing.be.utils.ResponseMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserDAO dao;

    public UserResource(UserDAO dao) {
        this.dao = dao;
    }

    @Timed
    @GET
    @UnitOfWork
    public List<User> getUsers(@Auth @Context SecurityContext context) {
        return dao.findAll();
    }

    @Timed
    @Path("/{username}")
    @GET
    @UnitOfWork
    public Response getUserByUsername(@Context SecurityContext context, @Valid @NotNull @PathParam("username") String username) {
        User user = dao.findByUsername(username);
        if (user != null) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage(404, "User not found")).build();
    }

    @Timed
    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@NotNull User user) {
        if (dao.findByUsername(user.getCredentials().getUsername()) == null) {
            long id = dao.save(user);
            if (id != 0) {
                return Response.created(null).build();
            }
        } else {
            return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(409, "Username already exists")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(400, "Bad request")).build();
    }

}
