package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.Car;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.User;
import rs.elfak.bobans.carsharing.be.models.daos.CarDAO;
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
@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    private final CarDAO carDAO;
    private final UserDAO userDAO;

    public CarResource(CarDAO carDAO, UserDAO userDAO) {
        this.carDAO = carDAO;
        this.userDAO = userDAO;
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    public List<Car> getCars(@Context SecurityContext context) {
        return carDAO.findAll();
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/{carId}")
    public Car getCarById(@Context SecurityContext context, @PathParam("carId") long carId) {
        return carDAO.findById(carId);
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(@Context SecurityContext context, @NotNull @Valid Car car) {
        long id = carDAO.save(car);
        if (id != 0) {
            User user = ((Credentials) context.getUserPrincipal()).getUser();
            user.addCar(car);
            userDAO.save(user);
            return Response.created(null).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't save car")).build();
    }

}
