package rs.elfak.bobans.carsharing.be.models;

import java.util.List;

/**
 * Created by Boban Stajic.
 *
 * @author Boban Stajic<bobanstajic@gmail.com>
 */
public class JsonSerializableCollection<T> {

    protected List<T> items;

    public JsonSerializableCollection(List<T> items) {
        this.items = items;
    }

    public void add(T item) {
        this.items.add(item);
    }

    public List<T> getItems() {
        return items;
    }

}
