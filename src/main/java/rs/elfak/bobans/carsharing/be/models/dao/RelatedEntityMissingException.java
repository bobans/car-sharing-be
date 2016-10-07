package rs.elfak.bobans.carsharing.be.models.dao;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class RelatedEntityMissingException extends RuntimeException {

    private final long id;
    private final String entity;


    public RelatedEntityMissingException(long id, String entity) {
        this.id = id;
        this.entity = entity;
    }

    public long getId() {
        return id;
    }

    public String getEntity() {
        return entity;
    }

}
