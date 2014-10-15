package domain.device.output.impl;

import domain.device.output.OutputDevice;

/**
 * @author Alexey
 */
public class Printer implements OutputDevice<String> {
    private final StringBuilder stringBuilder = new StringBuilder();
    @Override
    public void write(String string) {
        stringBuilder.append(string);
    }

    public String getOut() {
        return stringBuilder.toString();
    }
}
