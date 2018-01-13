package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import rs.elfak.bobans.carsharing.be.models.Make;
import rs.elfak.bobans.carsharing.be.models.Model;
import rs.elfak.bobans.carsharing.be.models.daos.MakeDAO;
import rs.elfak.bobans.carsharing.be.models.daos.ModelDAO;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/makes")
@Api(value = "/makes", description = "Car makes listing")
@Produces(MediaType.APPLICATION_JSON)
public class MakeModelResource {

    private MakeDAO makeDAO;
    private ModelDAO modelDAO;

    public MakeModelResource(MakeDAO makeDAO, ModelDAO modelDAO) {
        this.makeDAO = makeDAO;
        this.modelDAO = modelDAO;
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    public List<Make> getMakes() {
        return makeDAO.findAll();
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/{make_id}")
    public Make getMakeById(@PathParam("make_id") long makeId) {
        return makeDAO.findById(makeId);
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/{make_id}/models")
    public List<Model> getModelsByMake(@PathParam("make_id") long makeId) {
        return modelDAO.findByMake(makeId);
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    @Path("/{make_id}/models/{model_id}")
    public Model getModelsByMake(@PathParam("make_id") long makeId, @PathParam("model_id") long modelId) {
        return modelDAO.findByMakeAndModel(makeId, modelId);
    }

}
