package rs.elfak.bobans.carsharing.be.exceptionMappers;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import io.dropwizard.jersey.errors.ErrorMessage;
import rs.elfak.bobans.carsharing.be.models.daos.RelatedEntityMissingException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import static com.codahale.metrics.MetricRegistry.name;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class RelatedEntityMissingExceptionMapper implements ExceptionMapper<RelatedEntityMissingException> {

    private final Meter exceptions;

    public RelatedEntityMissingExceptionMapper(MetricRegistry metrics) {
        exceptions = metrics.meter(name(getClass(), "exceptions"));
    }

    @Override
    public Response toResponse(RelatedEntityMissingException e) {
        exceptions.mark();
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(new ErrorMessage(Response.Status.BAD_REQUEST.getStatusCode(),
                        "Related entity missing", "Entity " + e.getEntity() + " with id " + e.getId() + " missing"))
                .build();
    }


}
