package domain;


/**
 * @author Alexey
 */
public interface InputDevice<T> extends Device {
    Object read(T object) throws Exception;
}
