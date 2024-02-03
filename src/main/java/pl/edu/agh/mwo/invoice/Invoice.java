package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {


    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
       // this.products.add(product);
        // TODO: implement
    }

    public void addProduct(Product product, Integer quantity) {


        // TODO: implement
    }

    public BigDecimal getNetPrice() {
        BigDecimal netPrice = new BigDecimal(0);
        for (Product product : products.keySet()) {
            netPrice = netPrice.add(product.getPrice());
        }

        return netPrice;
    }

    public BigDecimal getTax() {
        return BigDecimal.ZERO;
    }

    public BigDecimal getGrossPrice() {
        return BigDecimal.ZERO;
    }
}
