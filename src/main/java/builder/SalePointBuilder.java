package builder;

import domain.ProductStorage;
import domain.device.input.BarCodesScanner;
import domain.device.output.LcdDisplay;
import domain.device.output.Printer;
import service.ProductService;
import service.SalePointImpl;

/**
 * @author Alexey
 */
public class SalePointBuilder {
    public static SalePointImpl build(ProductStorage productStorage) {
        SalePointImpl salePoint = new SalePointImpl(new ProductService(productStorage));
        salePoint.addInputDevice(new BarCodesScanner());
        salePoint.addOutputDevice(new Printer());
        salePoint.addOutputDevice(new LcdDisplay());
        return salePoint;
    }
}
