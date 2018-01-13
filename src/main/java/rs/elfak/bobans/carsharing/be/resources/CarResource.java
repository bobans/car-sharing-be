package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import rs.elfak.bobans.carsharing.be.models.AppUser;
import rs.elfak.bobans.carsharing.be.models.Car;
import rs.elfak.bobans.carsharing.be.models.Credentials;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/cars")
@Api(value = "/cars", description = "Users' cars management")
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
    public List<Car> getUserCars(@Context SecurityContext context) {
        AppUser user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
        if (user != null) {
            return user.getCars();
        }
        return new ArrayList<>();
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/{car_id}")
    public Car getCarById(@Context SecurityContext context, @PathParam("car_id") long carId) {
        return carDAO.findById(carId);
    }

    @Timed
    @PUT
    @UnitOfWork
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(@Context SecurityContext context, @NotNull @Valid Car car) {
        long id = carDAO.save(car);
        if (id != 0) {
            AppUser user = userDAO.findByUsername(((Credentials) context.getUserPrincipal()).getUsername());
            if (user != null) {
                user.addCar(car);
                userDAO.save(user);
                return Response.created(null).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "User not created")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't save car")).build();
    }

}
