package rs.elfak.bobans.carsharing.be.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import rs.elfak.bobans.carsharing.be.models.*;
import rs.elfak.bobans.carsharing.be.models.daos.SharedDriveDAO;
import rs.elfak.bobans.carsharing.be.models.daos.UserDAO;
import rs.elfak.bobans.carsharing.be.models.daos.UserReviewDAO;
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
@Path("/users/{username}/reviews")
@Api(value = "/users", description = "User review management")
@Produces(MediaType.APPLICATION_JSON)
public class UserReviewResource {

    private final UserDAO userDAO;
    private final UserReviewDAO userReviewDAO;
    private final SharedDriveDAO sharedDriveDAO;

    public UserReviewResource(UserDAO userDAO, UserReviewDAO userReviewDAO, SharedDriveDAO sharedDriveDAO) {
        this.userDAO = userDAO;
        this.userReviewDAO = userReviewDAO;
        this.sharedDriveDAO = sharedDriveDAO;
    }

    @Timed
    @GET
    @UnitOfWork
    @PermitAll
    public List<UserReview> getUserReviews(@Context SecurityContext context, @Valid @NotNull @PathParam("username") String username) {
        return userReviewDAO.findForUser(username);
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Path("/driver/{driveId}")
    public Response reviewDriver(@Context SecurityContext context, @Valid @NotNull @PathParam("username") String username, @Valid @NotNull @PathParam("driveId") long driveId, @NotNull ReviewRequest review) {
        if (username.compareTo(((Credentials) context.getUserPrincipal()).getUsername()) != 0) {
            AppUser reviewer = ((Credentials) context.getUserPrincipal()).getUser();
            AppUser driver = userDAO.findByUsername(username);
            SharedDrive sharedDrive = sharedDriveDAO.findById(driveId);
            UserReview exist = userReviewDAO.find(username, ((Credentials) context.getUserPrincipal()).getUsername(), driveId);
            if (exist == null) {
                if (reviewer != null && driver != null && sharedDrive != null) {
                    UserReview userReview = new UserReview();
                    userReview.setUser(driver);
                    userReview.setUserType(AppUser.TYPE_DRIVER);
                    userReview.setReviewer(reviewer);
                    userReview.setReviewerType(AppUser.TYPE_PASSENGER);
                    userReview.setScore(review.getScore());
                    userReview.setComment(review.getComment());
                    userReview.setSharedDrive(sharedDrive);
                    long id = userReviewDAO.save(userReview);
                    if (id > 0) {
                        return Response.created(null).build();
                    }
                }
            } else {
                return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(Response.Status.CONFLICT.getStatusCode(), "Already reviewed")).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ResponseMessage(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Can't review yourself")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't create review")).build();
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Path("/passenger/{driveId}")
    public Response reviewPassenger(@Context SecurityContext context, @Valid @NotNull @PathParam("username") String username, @Valid @NotNull @PathParam("driveId") long driveId, @NotNull ReviewRequest review) {
        if (username.compareTo(((Credentials) context.getUserPrincipal()).getUsername()) != 0) {
            AppUser reviewer = ((Credentials) context.getUserPrincipal()).getUser();
            AppUser passenger = userDAO.findByUsername(username);
            SharedDrive sharedDrive = sharedDriveDAO.findById(driveId);
            UserReview exist = userReviewDAO.find(username, ((Credentials) context.getUserPrincipal()).getUsername(), driveId);
            if (exist == null) {
                if (reviewer != null && passenger != null && sharedDrive != null) {
                    UserReview userReview = new UserReview();
                    userReview.setUser(passenger);
                    userReview.setUserType(AppUser.TYPE_PASSENGER);
                    userReview.setReviewer(reviewer);
                    userReview.setReviewerType(AppUser.TYPE_DRIVER);
                    userReview.setScore(review.getScore());
                    userReview.setComment(review.getComment());
                    userReview.setSharedDrive(sharedDrive);
                    long id = userReviewDAO.save(userReview);
                    if (id > 0) {
                        return Response.created(null).build();
                    }
                }
            } else {
                return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(Response.Status.CONFLICT.getStatusCode(), "Already reviewed")).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ResponseMessage(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Can't review yourself")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't create review")).build();
    }

    @Timed
    @POST
    @UnitOfWork
    @PermitAll
    @Path("/companion/{driveId}")
    public Response reviewCompanion(@Context SecurityContext context, @Valid @NotNull @PathParam("username") String username, @Valid @NotNull @PathParam("driveId") long driveId, @NotNull ReviewRequest review) {
        if (username.compareTo(((Credentials) context.getUserPrincipal()).getUsername()) != 0) {
            AppUser reviewer = ((Credentials) context.getUserPrincipal()).getUser();
            AppUser passenger = userDAO.findByUsername(username);
            SharedDrive sharedDrive = sharedDriveDAO.findById(driveId);
            UserReview exist = userReviewDAO.find(username, ((Credentials) context.getUserPrincipal()).getUsername(), driveId);
            if (exist == null) {
                if (reviewer != null && passenger != null && sharedDrive != null) {
                    UserReview userReview = new UserReview();
                    userReview.setUser(passenger);
                    userReview.setUserType(AppUser.TYPE_PASSENGER);
                    userReview.setReviewer(reviewer);
                    userReview.setReviewerType(AppUser.TYPE_PASSENGER);
                    userReview.setScore(review.getScore());
                    userReview.setComment(review.getComment());
                    userReview.setSharedDrive(sharedDrive);
                    long id = userReviewDAO.save(userReview);
                    if (id > 0) {
                        return Response.created(null).build();
                    }
                }
            } else {
                return Response.status(Response.Status.CONFLICT).entity(new ResponseMessage(Response.Status.CONFLICT.getStatusCode(), "Already reviewed")).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ResponseMessage(Response.Status.NOT_ACCEPTABLE.getStatusCode(), "Can't review yourself")).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseMessage(Response.Status.BAD_REQUEST.getStatusCode(), "Can't create review")).build();
    }

}
