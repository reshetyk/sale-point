package builder;

import domain.*;

/**
 * @author Alexey
 */
public class SalePointBuilder {
    public static SalePointImpl build(ProductStorage productStorage) {
        SalePointImpl salePoint = new SalePointImpl(productStorage);
        salePoint.addInputDevice(new BarCodesScanner());
        salePoint.addOutputDevice(new Printer());
        salePoint.addOutputDevice(new LcdDisplay());
        return salePoint;
    }
}