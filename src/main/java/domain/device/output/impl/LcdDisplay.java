package domain.device.output.impl;

import domain.device.output.OutputDevice;

/**
 * @author Alexey
 */
public class LcdDisplay implements OutputDevice<String> {
    private final StringBuilder out = new StringBuilder();

    @Override
    public void write(String message) {
        out.append(message).append('\n');
    }

    public String getOut() {
        return out.toString().trim();
    }
}
