package domain;

/**
 * @author Alexey
 */
public interface OutputDevice<T> extends Device {
    void write(T object);
}
