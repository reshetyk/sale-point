package service;

import builder.ReceiptBuilder;
import domain.BarCode;
import domain.Product;
import domain.Receipt;
import domain.device.Device;
import domain.device.input.BarCodesScanner;
import domain.device.input.InputDevice;
import domain.device.output.LcdDisplay;
import domain.device.output.OutputDevice;
import domain.device.output.Printer;
import exception.InvalidBarCodeException;
import serializer.ReceiptSerializer;

import java.util.*;


/**
 * @author Alexey
 */
public class SalePointImpl implements SalePoint {
    private final ProductService productService;
    private final Collection<InputDevice> inputDevices = new ArrayList<>();
    private final Collection<OutputDevice> outputDevices = new ArrayList<>();
    private final ReceiptBuilder receiptBuilder = new ReceiptBuilder();

    public SalePointImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void scanAndFindInDbAndShowStatusOnLcd(String sBarCode) {
        BarCodesScanner barCodesScanner = (BarCodesScanner) getDevice(BarCodesScanner.class);
        LcdDisplay lcdDisplay = (LcdDisplay) getDevice(LcdDisplay.class);
        String displayMessage;
        try {
            final Product product = productService.findProductByBarCode(barCodesScanner.read(sBarCode));
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
    public Collection<InputDevice> getInputDevices() {
        return Collections.unmodifiableCollection(inputDevices);
    }

    @Override
    public Collection<OutputDevice> getOutputDevices() {
        return Collections.unmodifiableCollection(outputDevices);
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
}
