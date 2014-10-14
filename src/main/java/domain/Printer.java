package domain;

/**
 * @author Alexey
 */
public class Printer implements OutputDevice<String>{
    private final StringBuilder stringBuilder = new StringBuilder();
    @Override
    public void write(String string) {
        stringBuilder.append(string);
    }

    public String getOut() {
        return stringBuilder.toString();
    }
}
