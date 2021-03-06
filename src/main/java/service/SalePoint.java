package service;

import domain.device.Device;
import domain.device.input.InputDevice;
import domain.device.output.OutputDevice;

import java.util.Collection;
import java.util.Set;

/**
 * @author Alexey
 */
public interface SalePoint {
    Collection<InputDevice> getInputDevices();

    Collection<OutputDevice> getOutputDevices();

    Device getDevice(Class<? extends Device> deviceClass);

    void scanAndFindInDbAndShowStatusOnLcd(String barCode);

    void exit();
}
