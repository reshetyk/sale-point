package domain.device.output;

/**
 * @author Alexey
 */
public class LcdDisplay implements OutputDevice<String> {
    private final StringBuilder out = new StringBuilder();

    @Override
    public void write(String message) {
        out.append(message).append('\n');
    }

    @Override
    public void reset() {
        out.setLength(0);
    }

    public String getOut() {
        return out.toString().trim();
    }
}
