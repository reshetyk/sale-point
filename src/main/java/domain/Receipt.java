package domain;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Alexey
 */
public class Receipt implements Iterable<Receipt.Item>{

    private final Collection<Item> items;

    public Receipt(Collection<Item> items) {
        this.items = items;
    }

    @Override
    public Iterator<Item> iterator() {
        return items.iterator();
    }

    /**
     * @author Alexey
     */
    public static class Item {
        private String name;
        private double price;

        public Item(String name, Double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public Double getPrice() {
            return price;
        }
    }


}
