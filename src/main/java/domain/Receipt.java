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
        private final BarCode barCode;
        private final String name;
        private final Price price;

        public Item(BarCode barCode, String name, Price price) {
            this.barCode = barCode;
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public Price getPrice() {
            return price;
        }

        public BarCode getBarCode() {
            return barCode;
        }
    }


}
