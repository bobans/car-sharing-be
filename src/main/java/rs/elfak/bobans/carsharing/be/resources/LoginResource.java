package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.*;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.Token;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.Base64;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/login")
@Api(value = "/login", description = "User authentication")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @ApiOperation(
            value = "Login",
            notes = "Returns authorization token for the user if credentials are correct",
            response = Token.class,
            authorizations = { @Authorization(value = "Basic") }
    )
    @ApiResponses(
            value = @ApiResponse(code = 401, message = "Unauthorized")
    )
    public Response login(@Context SecurityContext context) {
        Credentials credentials = (Credentials) context.getUserPrincipal();
        String token = credentials.getName() + ":" + credentials.getPassword();
        token = "Basic " + Base64.getEncoder().encodeToString(token.getBytes());
        return Response.ok(new Token(token)).build();
    }

}
