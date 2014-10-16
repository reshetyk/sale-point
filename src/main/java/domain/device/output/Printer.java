package domain.device.output;

/**
 * @author Alexey
 */
public class Printer implements OutputDevice<String> {
    private final StringBuilder stringBuilder = new StringBuilder();
    @Override
    public void write(String string) {
        stringBuilder.append(string);
    }

    @Override
    public void reset() {
        stringBuilder.setLength(0);
    }

    public String getOut() {
        return stringBuilder.toString();
    }
}
