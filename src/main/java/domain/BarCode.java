package domain;

/**
 * @author Alexey
 */
public class BarCode {
    private String barCode;

    public BarCode(String barCode) {
        this.barCode = barCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BarCode barCode1 = (BarCode) o;

        if (barCode != null ? !barCode.equals(barCode1.barCode) : barCode1.barCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return barCode != null ? barCode.hashCode() : 0;
    }
}
