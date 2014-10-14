package domain;

/**
 * @author Alexey
 */
public class BarCodesScanner implements InputDevice<String> {

    @Override
    public BarCode read(String barCode) throws InvalidBarCodeException {
        if (barCode == null || barCode.isEmpty()) {
            throw new InvalidBarCodeException();
        }
        return new BarCode(barCode);
    }
}
