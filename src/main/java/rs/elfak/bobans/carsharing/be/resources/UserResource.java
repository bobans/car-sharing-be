package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.User;
import rs.elfak.bobans.carsharing.be.models.daos.CredentialsDAO;
import rs.elfak.bobans.carsharing.be.models.daos.UserDAO;
import rs.elfak.bobans.carsharing.be.utils.ResponseMessage;

import javax.annotation.security.PermitAll;
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
    private final CredentialsDAO credentialsDAO;

    public UserResource(UserDAO dao, CredentialsDAO credentialsDAO) {
        this.dao = dao;
        this.credentialsDAO = credentialsDAO;
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    public List<User> getUsers(@Context SecurityContext context) {
        return dao.findAll();
    }

    @Timed
    @Path("/{username}")
    @GET
    @UnitOfWork
    @PermitAll
    public Response getUserByUsername(@Context SecurityContext context, @Valid @NotNull @PathParam("username") String username) {
        User user = dao.findByUsername(username);
        if (user != null) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage(404, "User not found")).build();
    }

    @Timed
    @Path("/me")
    @GET
    @UnitOfWork
    @PermitAll
    public Response getCurrentUser(@Context SecurityContext context) {
        User user = dao.findByUsername(((User) context.getUserPrincipal()).getUsername());
        if (user != null) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage(404, "User not found")).build();
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@Context SecurityContext context, @NotNull User user) {
        if (((Credentials) context.getUserPrincipal()).getUser() == null) {
            if (dao.findByUsername(user.getUsername()) == null) {
                if (((Credentials) context.getUserPrincipal()).getUsername().equals(user.getUsername())) {
                    long id = dao.save(user);
                    if (id != 0) {
                        Credentials credentials = (Credentials) context.getUserPrincipal();
                        credentials.setUser(user);
                        credentialsDAO.save(credentials);
                        return Response.created(null).build();
                    }
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(400, "Username must be same as authenticated user")).build();
                }
            } else {
                return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(409, "Username already exists")).build();
            }
        } else {
            return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(409, "User already created")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(400, "Bad request")).build();
    }

}
