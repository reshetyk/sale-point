package domain;

import java.util.Set;

/**
 * @author Alexey
 */
public interface SalePoint {
    Set<InputDevice> getInputDevices();

    Set<OutputDevice> getOutputDevices();

    Device getDevice(Class<? extends Device> deviceClass);

    void doScan(String barCode);

    void exit();
}
