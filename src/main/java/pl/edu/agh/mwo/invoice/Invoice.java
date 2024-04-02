package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Collection<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        } else {
            products.add(product);
        }
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be gretter than zero");
        } else {
            for (Integer i = 0; i < quantity; i++) {
                addProduct(product);
            }
        }
    }

    public BigDecimal getSubtotal() {
        BigDecimal bigDecimal = new BigDecimal("0");

        Iterator<Product> productIterator = products.iterator();

        while (productIterator.hasNext()) {
            bigDecimal = bigDecimal.add(productIterator.next().getPrice());
        }
        ;

        return bigDecimal;
    }

    public BigDecimal getTax() {

        BigDecimal bigDecimal = new BigDecimal("0");

        Iterator<Product> productIterator = products.iterator();

        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            bigDecimal = bigDecimal.add(product.getPrice().multiply(product.getTaxPercent()));
        }
        ;

        return bigDecimal;
    }

    public BigDecimal getTotal() {
        BigDecimal bigDecimal = new BigDecimal("0");

        Iterator<Product> productIterator = products.iterator();

        while (productIterator.hasNext()) {
            bigDecimal = bigDecimal.add(productIterator.next().getPriceWithTax());
        }
        ;

        return bigDecimal;
    }
}
