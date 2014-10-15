package service;

import domain.BarCode;
import domain.Product;
import domain.ProductStorage;

/**
 * @author Alexey
 */
public class ProductService {

    private final ProductStorage productStorage;

    public ProductService(ProductStorage productStorage) {
        this.productStorage = productStorage;
    }

    public Product findProductByBarCode(BarCode barCode) {
        for (Product product : productStorage) {
            if (product.getBarCode() != null && barCode != null && product.getBarCode().equals(barCode)) {
                return product;
            }
        }
        return null;
    }
}
