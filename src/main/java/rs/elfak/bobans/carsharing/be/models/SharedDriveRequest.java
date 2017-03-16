package rs.elfak.bobans.carsharing.be.models;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class SharedDriveRequest {

    private SharedDrive sharedDrive;
    private Passenger passenger;

    public SharedDriveRequest() {
    }

    public SharedDriveRequest(SharedDrive sharedDrive, Passenger passenger) {
        this.sharedDrive = sharedDrive;
        this.passenger = passenger;
    }

    public SharedDrive getSharedDrive() {
        return sharedDrive;
    }

    public void setSharedDrive(SharedDrive sharedDrive) {
        this.sharedDrive = sharedDrive;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

}
