package domain.device.input;


import domain.device.Device;

/**
 * @author Alexey
 */
public interface InputDevice<T> extends Device {
    Object read(T object) throws Exception;
}
