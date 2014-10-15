package domain;

/**
 * @author Alexey
 */
public class Product {
    private String name;
    private Price price;
    private BarCode barCode;

    public Product() {
    }

    public Product(String name, Price price, BarCode barCode) {
        this.name = name;
        this.price = price;
        this.barCode = barCode;
    }


    public BarCode getBarCode() {
        return barCode;
    }

    public void setBarCode(BarCode barCode) {
        this.barCode = barCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (barCode != null ? !barCode.equals(product.barCode) : product.barCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return barCode != null ? barCode.hashCode() : 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
}
