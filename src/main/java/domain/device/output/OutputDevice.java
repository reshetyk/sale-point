package domain.device.output;

import domain.device.Device;

/**
 * @author Alexey
 */
public interface OutputDevice<T> extends Device {
    void write(T object);
}
