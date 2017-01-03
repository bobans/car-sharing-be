package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.joda.time.DateTime;
import rs.elfak.bobans.carsharing.be.models.SharedDrive;
import rs.elfak.bobans.carsharing.be.models.User;
import rs.elfak.bobans.carsharing.be.models.daos.SharedDriveDAO;
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
@Path("/drives")
@Produces(MediaType.APPLICATION_JSON)
public class SharedDriveResource {

    private final SharedDriveDAO dao;

    public SharedDriveResource(SharedDriveDAO dao) {
        this.dao = dao;
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    public List<SharedDrive> getDrives(@Context SecurityContext context, @QueryParam("offset") int offset, @QueryParam("limit") int limit) {
        return dao.findPaged(offset, limit);
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/{id}")
    public SharedDrive getDrive(@Context SecurityContext context, @PathParam("id") long id) {
        return dao.findById(id);
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/user/{username}")
    public List<SharedDrive> getByUser(@Context SecurityContext context, @PathParam("username") String username) {
        return dao.findByUser(username);
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    public Response addDrive(@Context SecurityContext context, @NotNull @Valid SharedDrive drive) {
        long id = dao.save(drive);
        if (id > 0) {
            return Response.created(null).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't save drive")).build();
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/filter")
    public List<SharedDrive> filterDrives(@Context SecurityContext context, @QueryParam("date") String date, @QueryParam("repeatDays") String repeatDays, @QueryParam("offset") int offset, @QueryParam("limit") int limit) {
        return dao.filterDrives(DateTime.parse(date), repeatDays, offset, limit);
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Path("{id}/passenger")
    public Response addPassengerToDrive(@Context SecurityContext context, @PathParam("id") long driveId, @NotNull @Valid User passenger) {
        SharedDrive drive = dao.findById(driveId);
        if (drive != null) {
            drive.addPassenger(passenger);
            long id = dao.save(drive);
            if (id > 0) {
                return Response.ok().build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't save drive")).build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage(Response.Status.NOT_FOUND.getStatusCode(), "Drive not found")).build();
    }

}
