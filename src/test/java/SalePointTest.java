import builder.SalePointBuilder;
import domain.BarCode;
import domain.Price;
import domain.Product;
import domain.ProductStorage;
import domain.device.input.impl.BarCodesScanner;
import domain.device.output.impl.LcdDisplay;
import domain.device.output.impl.Printer;
import org.junit.Before;
import org.junit.Test;
import service.SalePointImpl;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Alexey
 */
public class SalePointTest {

    private ProductStorage productStorage;

    @Before
    public void setUp() throws Exception {
        productStorage = new ProductStorage(Arrays.asList(
                new Product("Butter", new Price(11.0), new BarCode("111")),
                new Product("Beer", new Price(33.6), new BarCode("222")),
                new Product("Milk", new Price(20.83), new BarCode("333")))
        );
    }

    /**
     * Check that sale point has one input device
     */
    @Test
    public void salePointHasOneInputDevice() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        assertEquals(1, salePoint.getInputDevices().size());
    }

    /**
     * Check that sale point has one input device: bar codes scanner
     */
    @Test
    public void salePointHasBarCodesScannerInInputDevices() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        assertThat(salePoint.getInputDevices(), contains(instanceOf(BarCodesScanner.class)));
    }

    /**
     * Check that sale point has two output devices
     */
    @Test
    public void salePointHasTwoOutputDevices() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        assertEquals(2, salePoint.getOutputDevices().size());
    }

    /**
     * Check that sale point has two output devices: LCD display and printer
     */
    @Test
    public void salePointHasLcdDisplayAndPrinterInOutputDevices() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        assertThat(salePoint.getOutputDevices(),
                containsInAnyOrder(instanceOf(LcdDisplay.class), instanceOf(Printer.class)));
    }


    /**
     * Check products bar code is scanned and
     * if the product is found in products database than
     * it's name and price is printed on LCD display
     */
    @Test
    public void ifProductFoundThanNameAndPricePrintedOnLcdDisplay() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        salePoint.scanAndFindInDbAndShowStatusOnLcd("111");
        final LcdDisplay lcdDisplay = (LcdDisplay) salePoint.getDevice(LcdDisplay.class);
        assertEquals("Butter : 11.0", lcdDisplay.getOut());
    }

    /**
     * Check products bar code is scanned and
     * if the product is not found than
     * error message 'Product not found' is printed on LCD  display
     */
    @Test
    public void ifProductNotFoundThanErrorMessageOnLcdDisplay() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        salePoint.scanAndFindInDbAndShowStatusOnLcd("0000");
        final LcdDisplay lcdDisplay = (LcdDisplay) salePoint.getDevice(LcdDisplay.class);
        assertEquals("Product not found", lcdDisplay.getOut());
    }

    /**
     * Check that if the code scanned is empty than error message 'Invalid bar-code' is printed on LCD display
     */
    @Test
    public void ifCodeScannedIsEmptyThanErrorMessageOnLcdDisplay() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        salePoint.scanAndFindInDbAndShowStatusOnLcd("");
        final LcdDisplay lcdDisplay = (LcdDisplay) salePoint.getDevice(LcdDisplay.class);
        assertEquals("Invalid bar-code", lcdDisplay.getOut());
    }

    /**
     * Check that when 'exit' is input than receipt is printed on printer containing a list of all previously
     * scanned items names and prices as well as total sum to be paid for all items; the total sum is
     * also printed on LCD display
     */
    @Test
    public void whenExitInputThanReceiptWithAllScannedItemsPrintedOnPrinter() {
        SalePointImpl salePoint = SalePointBuilder.build(productStorage);
        salePoint.scanAndFindInDbAndShowStatusOnLcd("111");
        salePoint.scanAndFindInDbAndShowStatusOnLcd("222");
        salePoint.exit();
        final LcdDisplay lcdDisplay = (LcdDisplay) salePoint.getDevice(LcdDisplay.class);
        final Printer printer = (Printer) salePoint.getDevice(Printer.class);
        assertEquals("Butter : 11.0\nBeer : 33.6\nTotal : 44.6", lcdDisplay.getOut());
        assertEquals("Butter : 11.0\nBeer : 33.6\nTotal : 44.6", printer.getOut());
    }

}
