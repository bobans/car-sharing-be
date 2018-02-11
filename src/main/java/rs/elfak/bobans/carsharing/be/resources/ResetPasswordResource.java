package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import rs.elfak.bobans.carsharing.be.models.AppUser;
import rs.elfak.bobans.carsharing.be.models.Credentials;
import rs.elfak.bobans.carsharing.be.models.daos.CredentialsDAO;
import rs.elfak.bobans.carsharing.be.models.daos.UserDAO;
import rs.elfak.bobans.carsharing.be.utils.EmailUtils;
import rs.elfak.bobans.carsharing.be.utils.ResponseMessage;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
@Path("/reset-password")
@Api(value = "/reset-password", description = "Password reset")
@Produces(MediaType.APPLICATION_JSON)
public class ResetPasswordResource {

    private UserDAO userDAO;
    private CredentialsDAO credentialsDAO;

    public ResetPasswordResource(UserDAO userDAO, CredentialsDAO credentialsDAO) {
        this.userDAO = userDAO;
        this.credentialsDAO = credentialsDAO;
    }

    @Timed
    @POST
    @ApiOperation(
            value = "Reset password",
            notes = "Sends reset password link to email if email is found"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 202, message = "Accepted", response = ResponseMessage.class)
            }
    )
    @UnitOfWork
    public Response resetPassword(@Valid @NotNull @QueryParam("email") String email) {
        AppUser user = userDAO.findByEmail(email);
        if (user != null) {
            Credentials credentials = credentialsDAO.findByUsername(user.getUsername());
            if (credentials != null) {
                EmailUtils.resetPasswordEmail(email, credentials.getUsername(), credentials.getPassword());
            } else {
                EmailUtils.resetPasswordEmail(email, null, null);
            }
        } else {
            EmailUtils.resetPasswordEmail(email, null, null);
        }
        return Response.accepted().entity(new ResponseMessage(Response.Status.ACCEPTED.getStatusCode(), "Accepted")).build();
    }
}
