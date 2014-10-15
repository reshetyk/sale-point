package domain.device.input.impl;

import domain.BarCode;
import domain.device.input.InputDevice;
import exception.InvalidBarCodeException;

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
