package builder;

import domain.Receipt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexey
 */
public class ReceiptBuilder {
    private final List<Receipt.Item> receiptItems = new ArrayList<>();

    public ReceiptBuilder append(Receipt.Item item) {
        receiptItems.add(item);
        return this;
    }
    public Receipt build () {
        return new Receipt(receiptItems);
    }

}
