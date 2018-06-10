package rs.elfak.bobans.carsharing.be.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.joda.time.DateTime;
import rs.elfak.bobans.carsharing.be.models.AppUser;
import rs.elfak.bobans.carsharing.be.models.Passenger;
import rs.elfak.bobans.carsharing.be.models.SharedDrive;
import rs.elfak.bobans.carsharing.be.models.SharedDriveRequest;
import rs.elfak.bobans.carsharing.be.models.firebase.FirebaseMessage;
import rs.elfak.bobans.carsharing.be.models.firebase.FirebaseMessageData;
import rs.elfak.bobans.carsharing.be.models.firebase.FirebaseToken;
import rs.elfak.bobans.carsharing.be.utils.serialization.DateTimeSerializer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class FirebaseManager {

    private static final String FIREBASE_SERVER_KEY = "AAAAjDxAqGs:APA91bH8ics7tNhEcZ-DI0k9gKXBwAF9aRQjHSCIbR6bHrwyoFXuPRpzAEEnFetlCzzs_7vVkXcHzFV28VKlkMavdyMQWXq8We6zvkZrDbGDrJGLqRUhe-HKShOgekYrh33U_g_7SH0U";

    private static final String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";

    public static <T> void sendPushNotification(String to, T data) {
        sendPushNotification(new FirebaseMessage<>(to, data));
    }

    public static void sendPushNotification(FirebaseMessage message) {
        Client client = ClientBuilder.newBuilder().build();
        final WebTarget webTarget = client.target(FIREBASE_URL);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<DateTime>(){}.getType(), new DateTimeSerializer())
                .create();

        String json = gson.toJson(message);

        Response response = webTarget.request()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .header("Authorization", "key=" + FIREBASE_SERVER_KEY)
                .post(Entity.json(json));

        System.out.println("Firebase message:" + (json != null ? json : ""));
        System.out.println("Firebase send message response: " + response.getStatus() + " : " + response.readEntity(String.class));
    }

    public static void notifyUserDriveRequested(SharedDrive drive, Passenger passenger) {
        for (FirebaseToken token : drive.getUser().getFirebaseTokens()) {
            sendPushNotification(token.getToken(), new FirebaseMessageData<>(FirebaseMessageData.MessageType.DRIVE_REQUESTED, new SharedDriveRequest(drive, passenger)));
        }
    }

    public static void notifyUserDriveRequestCanceled(SharedDrive drive, Passenger passenger) {
        for (FirebaseToken token : drive.getUser().getFirebaseTokens()) {
            sendPushNotification(token.getToken(), new FirebaseMessageData<>(FirebaseMessageData.MessageType.DRIVE_REQUEST_CANCELED, new SharedDriveRequest(drive, passenger)));
        }
    }

    public static void notifyNewSharedDrive(List<AppUser> users, SharedDrive sharedDrive) {
        for (AppUser user : users) {
            for (FirebaseToken token : user.getFirebaseTokens()) {
                sendPushNotification(token.getToken(), new FirebaseMessageData<>(FirebaseMessageData.MessageType.NEW_DRIVE, sharedDrive));
            }
        }
    }

}
