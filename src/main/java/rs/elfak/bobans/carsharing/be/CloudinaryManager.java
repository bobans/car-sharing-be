package rs.elfak.bobans.carsharing.be;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class CloudinaryManager {

    private static CloudinaryManager instance;

    public static CloudinaryManager getInstance() {
        if (instance == null) {
            instance = new CloudinaryManager();
        }
        return instance;
    }

    private Cloudinary cloudinary;

    private CloudinaryManager() {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "car-sharing",
                "api_key", "623137126834368",
                "api_secret", "3zBwN-fcic6HjJZfMiGaErc_awc"
        ));
    }

    public Cloudinary getCloudinary() {
        return cloudinary;
    }

}
