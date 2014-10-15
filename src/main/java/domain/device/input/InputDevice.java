package domain.device.input;


import domain.device.Device;

/**
 * @author Alexey
 */
public interface InputDevice<InputType, OutputType> extends Device {
    OutputType read(InputType object) throws Exception;
}
