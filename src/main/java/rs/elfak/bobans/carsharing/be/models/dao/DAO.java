package rs.elfak.bobans.carsharing.be.models.dao;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public interface DAO<T> {

    List<T> findAll();
    T findById(long id);
    long save(T obj);

}
