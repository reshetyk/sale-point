package serializer;

import domain.Receipt;

/**
 * @author Alexey
 */
public class ReceiptSerializer {

    public static String serializeAsString(Receipt receipt) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Receipt.Item item : receipt) {
            stringBuilder.append(serializeReceiptItemAsString(item)).append('\n');
        }
        return stringBuilder.toString();
    }

    public static String serializeReceiptItemAsString(Receipt.Item item) {
        return item.getName() + " : " + item.getPrice();
    }

}
