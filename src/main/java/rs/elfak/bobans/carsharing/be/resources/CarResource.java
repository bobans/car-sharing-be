package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import rs.elfak.bobans.carsharing.be.models.Car;
import rs.elfak.bobans.carsharing.be.models.dao.CarDAO;
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
@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    private final CarDAO dao;

    public CarResource(CarDAO dao) {
        this.dao = dao;
    }

    @Timed
    @GET
    @UnitOfWork
    public List<Car> getCars(@Context SecurityContext context) {
        return dao.findAll();
    }

    @Timed
    @GET
    @UnitOfWork
    @Path("/{carId}")
    public Car getCarById(@Context SecurityContext context, @PathParam("carId") long carId) {
        return dao.findById(carId);
    }

    @Timed
    @POST
    @UnitOfWork
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCar(@NotNull @Valid Car car) {
        long id = dao.save(car);
        if (id != 0) {
            return Response.created(null).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(400, "Bad request")).build();
    }

}
