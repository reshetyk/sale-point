package domain;

import builder.ReceiptBuilder;
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

    public void addInputDevice(InputDevice inputDevice) {
        inputDevices.add(inputDevice);
    }

    public void addOutputDevice(OutputDevice outputDevice) {
        outputDevices.add(outputDevice);
    }

    @Override
    public Set<InputDevice> getInputDevices() {
        return Collections.unmodifiableSet(inputDevices);
    }

    @Override
    public Set<OutputDevice> getOutputDevices() {
        return Collections.unmodifiableSet(outputDevices);
    }

    @Override
    public Device getDevice(Class<? extends Device> deviceClass) {
        Set<Device> devices = new HashSet<Device>(inputDevices.size() + outputDevices.size());
        devices.addAll(inputDevices);
        devices.addAll(outputDevices);
        for (Device device : devices) {
            if (deviceClass != null && deviceClass.equals(device.getClass())) {
                return device;
            }
        }
        throw new RuntimeException("Not found device by the class '" + deviceClass + "'");
    }

    @Override
    public void doScan(String sBarCode) {
        BarCodesScanner barCodesScanner = (BarCodesScanner) getDevice(BarCodesScanner.class);
        LcdDisplay lcdDisplay = (LcdDisplay) getDevice(LcdDisplay.class);
        String message;
        try {
            final Product product = findProductByBarCode(barCodesScanner.read(sBarCode));
            message = "Product not found";
            if (product != null) {
                final Receipt.Item item = new Receipt.Item(product.getName(), product.getPrice().getPrice());
                receiptBuilder.append(item);
                message = ReceiptSerializer.serializeReceiptItemAsString(item);
            }
        } catch (InvalidBarCodeException e) {
            message = "Invalid bar-code";
        }
        lcdDisplay.write(message);
    }

    @Override
    public void exit() {

        final Receipt receipt = receiptBuilder.build();
        final double total = getTotal(receipt);

        LcdDisplay lcdDisplay = (LcdDisplay) getDevice(LcdDisplay.class);
        lcdDisplay.write("Total : " + total);

        final Printer printer = (Printer) getDevice(Printer.class);
        printer.write(ReceiptSerializer.serializeAsString(receipt));
        printer.write("Total : " + total);

    }

    //TODO: move this method from this class
    private double getTotal(Receipt receipt) {
        double total = 0;
        for (Receipt.Item item : receipt) {
            total += item.getPrice();
        }
        return total;
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
