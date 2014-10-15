package service;

import domain.Price;
import domain.Receipt;

/**
 * @author Alexey
 */
public class ReceiptCalculator {
    public static Price countTotal(Receipt receipt) {
        double total = 0;
        for (Receipt.Item item : receipt) {
            total += item.getPrice().getPrice();
        }
        return new Price(total);
    }
}
