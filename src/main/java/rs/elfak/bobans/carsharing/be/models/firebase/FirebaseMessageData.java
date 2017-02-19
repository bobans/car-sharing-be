package rs.elfak.bobans.carsharing.be.models.firebase;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class FirebaseMessageData<T> {

    public enum MessageType {
        DRIVE_REQUESTED
    }

    private MessageType type;
    private T payload;

    public FirebaseMessageData() {
    }

    public FirebaseMessageData(MessageType type, T payload) {
        this.type = type;
        this.payload = payload;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

}
