package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import rs.elfak.bobans.carsharing.be.models.AppUser;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.daos.UserDAO;
import rs.elfak.bobans.carsharing.be.models.firebase.FirebaseToken;
import rs.elfak.bobans.carsharing.be.utils.ResponseMessage;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/fcm")
@Api(value = "/fcm", description = "Firebase management")
@Produces(MediaType.APPLICATION_JSON)
public class FCMResource {

    private final UserDAO userDAO;

    public FCMResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Path("/register")
    public Response register(@Context SecurityContext context, @NotNull FirebaseToken token) {
        AppUser user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            user.addFirebaseId(token.getToken());
            userDAO.save(user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "User not exist")).build();
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Path("/unregister")
    public Response unregister(@Context SecurityContext context, @NotNull FirebaseToken token) {
        AppUser user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            user.removeFirebaseId(token.getToken());
            userDAO.save(user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "User not exist")).build();
    }

}
