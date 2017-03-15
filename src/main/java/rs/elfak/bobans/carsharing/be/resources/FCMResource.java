package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import rs.elfak.bobans.carsharing.be.models.AppUser;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.daos.FirebaseTokenDAO;
import rs.elfak.bobans.carsharing.be.models.daos.UserDAO;
import rs.elfak.bobans.carsharing.be.models.firebase.FirebaseToken;
import rs.elfak.bobans.carsharing.be.utils.ResponseMessage;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
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
    private final FirebaseTokenDAO firebaseTokenDAO;

    public FCMResource(UserDAO userDAO, FirebaseTokenDAO firebaseTokenDAO) {
        this.userDAO = userDAO;
        this.firebaseTokenDAO = firebaseTokenDAO;
    }

    @Timed
    @PUT
    @UnitOfWork
    @PermitAll
    @Path("/register")
    public Response register(@Context SecurityContext context, @NotNull FirebaseToken token) {
        AppUser user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            String id = firebaseTokenDAO.save(token);
            if (id == null || id.isEmpty()) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ResponseMessage(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Can't save token")).build();
            }
            user.addFirebaseToken(token);
            userDAO.save(user);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "User not exist")).build();
    }

    @Timed
    @DELETE
    @UnitOfWork
    @PermitAll
    @Path("/unregister/{device_id}")
    public Response unregister(@Context SecurityContext context, @PathParam("device_id") String deviceId) {
        AppUser user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            FirebaseToken token = firebaseTokenDAO.findById(deviceId);
            if (token != null) {
                user.removeFirebaseToken(token);
                userDAO.save(user);
            }
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "User not exist")).build();
    }

}
