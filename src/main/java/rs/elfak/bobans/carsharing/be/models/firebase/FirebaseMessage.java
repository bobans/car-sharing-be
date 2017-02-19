package rs.elfak.bobans.carsharing.be.models.firebase;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class FirebaseMessage<T> {

    private String to;
    private T data;

    public FirebaseMessage() {
    }

    public FirebaseMessage(String to) {
        this.to = to;
    }

    public FirebaseMessage(String to, T data) {
        this.to = to;
        this.data = data;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
