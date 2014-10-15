package service;

import builder.ReceiptBuilder;
import domain.BarCode;
import domain.Product;
import domain.ProductStorage;
import domain.Receipt;
import domain.device.Device;
import domain.device.input.InputDevice;
import domain.device.input.impl.BarCodesScanner;
import domain.device.output.OutputDevice;
import domain.device.output.impl.LcdDisplay;
import domain.device.output.impl.Printer;
import exception.InvalidBarCodeException;
import serializer.ReceiptSerializer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * @author Alexey
 */
public class SalePointImpl implements SalePoint {
    private final Set<InputDevice> inputDevices = new HashSet<InputDevice>();
    private final Set<OutputDevice> outputDevices = new HashSet<OutputDevice>();
    private final ProductStorage productStorage;
    private final ReceiptBuilder receiptBuilder = new ReceiptBuilder();

    public SalePointImpl(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    @Override
    public void scanAndFindInDbAndShowStatusOnLcd(String sBarCode) {
        BarCodesScanner barCodesScanner = (BarCodesScanner) getDevice(BarCodesScanner.class);
        LcdDisplay lcdDisplay = (LcdDisplay) getDevice(LcdDisplay.class);
        String displayMessage;
        try {
            final Product product = findProductByBarCode(barCodesScanner.read(sBarCode));
            displayMessage = "Product not found";
            if (product != null) {
                final Receipt.Item item = new Receipt.Item(product.getName(), product.getPrice());
                receiptBuilder.append(item);
                displayMessage = ReceiptSerializer.serializeReceiptItemAsString(item);
            }
        } catch (InvalidBarCodeException e) {
            //TODO: log exception
            displayMessage = "Invalid bar-code";
        }
        lcdDisplay.write(displayMessage);
    }

    @Override
    public void exit() {
        countTotalPrintReceiptAndDisplay();
    }

    @Override
    public Set<InputDevice> getInputDevices() {
        return Collections.unmodifiableSet(inputDevices);
    }

    @Override
    public Set<OutputDevice> getOutputDevices() {
        return Collections.unmodifiableSet(outputDevices);
    }

    //TODO: can return list of devices
    @Override
    public Device getDevice(Class<? extends Device> deviceClass) {
        Set<Device> devices = new HashSet<>(inputDevices.size() + outputDevices.size());
        devices.addAll(inputDevices);
        devices.addAll(outputDevices);
        for (Device device : devices) {
            if (deviceClass != null && deviceClass.equals(device.getClass())) {
                return device;
            }
        }
        throw new RuntimeException("Not found device by the class '" + deviceClass + "'");
    }

    public void addInputDevice(InputDevice inputDevice) {
        inputDevices.add(inputDevice);
    }

    public void addOutputDevice(OutputDevice outputDevice) {
        outputDevices.add(outputDevice);
    }

    private void countTotalPrintReceiptAndDisplay() {
        final Receipt receipt = receiptBuilder.build();
        writeTotalOfReceipt((LcdDisplay) getDevice(LcdDisplay.class), receipt);

        final Printer printer = (Printer) getDevice(Printer.class);
        printer.write(ReceiptSerializer.serializeAsString(receipt));
        writeTotalOfReceipt(printer, receipt);
    }

    private void writeTotalOfReceipt(OutputDevice<String> outputDevice, Receipt receipt) {
        outputDevice.write("Total : " + ReceiptCalculator.countTotal(receipt));
    }

    //TODO: extract to Service method
    protected Product findProductByBarCode(BarCode barCode) {
        for (Product product : productStorage) {
            if (product.getBarCode() != null && barCode != null && product.getBarCode().equals(barCode)) {
                return product;
            }
        }
        return null;
    }
}
