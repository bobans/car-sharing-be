package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.AppUser;
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
    public List<AppUser> getUsers(@Context SecurityContext context) {
        return dao.findAll();
    }

    @Timed
    @Path("/{username}")
    @GET
    @UnitOfWork
    @PermitAll
    public AppUser getUserByUsername(@Context SecurityContext context, @Valid @NotNull @PathParam("username") String username) {
        return dao.findByUsername(username);
    }

    @Timed
    @Path("/me")
    @GET
    @UnitOfWork
    @PermitAll
    public AppUser getCurrentUser(@Context SecurityContext context) {
        return dao.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(@Context SecurityContext context, @NotNull AppUser user) {
        if (((Credentials) context.getUserPrincipal()).getUser() == null) {
            if (dao.findByUsername(((Credentials) context.getUserPrincipal()).getUsername()) == null) {
                user.setUsername(((Credentials) context.getUserPrincipal()).getUsername());
                long id = dao.save(user);
                if (id != 0) {
                    Credentials credentials = (Credentials) context.getUserPrincipal();
                    credentials.setUser(user);
                    credentialsDAO.save(credentials);
                    return Response.created(null).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't save user")).build();
                }
            } else {
                return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(Response.Status.CONFLICT.getStatusCode(), "Username already exists")).build();
            }
        } else {
            return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(Response.Status.CONFLICT.getStatusCode(), "User already created")).build();
        }
    }

}
