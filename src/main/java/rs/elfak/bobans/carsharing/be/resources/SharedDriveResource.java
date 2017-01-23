package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import org.joda.time.DateTime;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.Passenger;
import rs.elfak.bobans.carsharing.be.models.SharedDrive;
import rs.elfak.bobans.carsharing.be.models.User;
import rs.elfak.bobans.carsharing.be.models.daos.PassengerDAO;
import rs.elfak.bobans.carsharing.be.models.daos.SharedDriveDAO;
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
@Path("/drives")
@Produces(MediaType.APPLICATION_JSON)
public class SharedDriveResource {

    private final SharedDriveDAO dao;
    private final UserDAO userDAO;
    private final PassengerDAO passengerDAO;

    public SharedDriveResource(SharedDriveDAO dao, UserDAO userDAO, PassengerDAO passengerDAO) {
        this.dao = dao;
        this.userDAO = userDAO;
        this.passengerDAO = passengerDAO;
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
    @DELETE
    @UnitOfWork
    @PermitAll
    @Path("/{id}")
    public Response deleteDrive(@Context SecurityContext context, @PathParam("id") long id) {
        SharedDrive drive = dao.findById(id);
        if (drive != null) {
            dao.delete(drive);
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity(new ResponseMessage(Response.Status.NOT_FOUND.getStatusCode(), "Drive not found")).build();
    }

    @Timed
    @PUT
    @UnitOfWork
    @PermitAll
    @Path("/{driveId}/request/{passengerId}/{status}")
    public Response updateRequest(@Context SecurityContext context,
                                  @PathParam("driveId") long driveId,
                                  @PathParam("passengerId") long passengerId,
                                  @PathParam("status") short status) {
        User user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            SharedDrive drive = dao.findById(driveId);
            if (drive != null) {
                if (drive.getUser().getId() == user.getId()) {
                    for (Passenger passenger : drive.getPassengers()) {
                        if (passenger.getId() == passengerId) {
                            passenger.setStatus(status);
                            dao.save(drive);
                            return Response.ok().build();
                        }
                    }
                    return Response.status(Response.Status.NO_CONTENT).entity(new ResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "Passenger does not exist")).build();
                }
                return Response.status(Response.Status.FORBIDDEN).entity(new ResponseMessage(Response.Status.FORBIDDEN.getStatusCode(), "Only drive owner can update request")).build();
            }
            return Response.status(Response.Status.NO_CONTENT).entity(new ResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "Drive does not exist")).build();
        }
        return Response.status(Response.Status.NO_CONTENT).entity(new ResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "User does not exist")).build();
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Path("/{id}/request")
    public Response request(@Context SecurityContext context, @PathParam("id") long id) {
        User user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            SharedDrive drive = dao.findById(id);
            if (drive != null) {
                if (drive.getUser().getId() != user.getId()) {
                    boolean exists = false;
                    for (Passenger passenger : drive.getPassengers()) {
                        if (passenger.getUser().getId() == user.getId()) {
                            exists = true;
                        }
                    }
                    if (!exists) {
                        Passenger passenger = new Passenger(user);
                        long passengerId = passengerDAO.save(passenger);
                        if (passengerId != -1) {
                            drive.addPassenger(passenger);
                            dao.save(drive);
                            return Response.created(null).build();
                        }
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ResponseMessage(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Some error happened")).build();
                    }
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ResponseMessage(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Already requested")).build();
                }
                return Response.status(Response.Status.FORBIDDEN).entity(new ResponseMessage(Response.Status.FORBIDDEN.getStatusCode(), "Can't be passenger on your own drive")).build();
            }
            return Response.status(Response.Status.NO_CONTENT).entity(new ResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "Drive does not exist")).build();
        }
        return Response.status(Response.Status.NO_CONTENT).entity(new ResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "User does not exist")).build();
    }

    @Timed
    @DELETE
    @UnitOfWork
    @PermitAll
    @Path("/{id}/request")
    public Response cancelRequest(@Context SecurityContext context, @PathParam("id") long id) {
        User user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            SharedDrive drive = dao.findById(id);
            if (drive != null) {
                boolean exists = false;
                List<Passenger> passengers = drive.getPassengers();
                for (int i=passengers.size()-1; i>=0; i--) {
                    if (passengers.get(i).getUser().getId() == user.getId()) {
                        exists = true;
                        passengers.remove(i);
                    }
                }
                if (exists) {
                    drive.setPassengers(passengers);
                    dao.save(drive);
                }
                return Response.ok().build();
            }
            return Response.status(Response.Status.NO_CONTENT).entity(new ResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "Drive does not exist")).build();
        }
        return Response.status(Response.Status.NO_CONTENT).entity(new ResponseMessage(Response.Status.NO_CONTENT.getStatusCode(), "User does not exist")).build();
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

}
