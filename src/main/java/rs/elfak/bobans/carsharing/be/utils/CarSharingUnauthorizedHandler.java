package rs.elfak.bobans.carsharing.be.utils;

import io.dropwizard.auth.UnauthorizedHandler;

import javax.ws.rs.core.Response;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class CarSharingUnauthorizedHandler implements UnauthorizedHandler {

    @Override
    public Response buildResponse(String s, String s1) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(new ResponseMessage(401, "Unauthorized")).build();
    }

}
