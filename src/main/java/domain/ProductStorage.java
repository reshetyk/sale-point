package domain;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alexey
 */
public class ProductStorage implements Iterable<Product>{
    final private List<Product> products;

    public ProductStorage(List<Product> products) {
        this.products = products;
    }

    @Override
    public Iterator iterator() {
        return products.listIterator();
    }
}
