package builder;

import domain.ProductStorage;
import domain.device.input.impl.BarCodesScanner;
import domain.device.output.impl.LcdDisplay;
import domain.device.output.impl.Printer;
import service.SalePointImpl;

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
