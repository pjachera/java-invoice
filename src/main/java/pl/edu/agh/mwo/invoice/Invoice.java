package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    static Integer numberOfInvoice = 0;

    public Invoice() {
        numberOfInvoice++;
    }

    private Map<Product, Integer> products = new LinkedHashMap<Product, Integer>();

    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getNumber() {
        return numberOfInvoice;
    }

    public String print() {
        String allInvoice = "";
        if (!products.isEmpty()) {
            allInvoice = allInvoice.concat("Numer faktury:" + getNumber());
            for (Product product : products.keySet()) {

                allInvoice = allInvoice.concat("\nNazwa produktu:" + product.getName() +
                        " Liczba sztuk:" + products.get(product).intValue() +
                        " Cena:" + product.getPrice() + "zł");


            }
            allInvoice = allInvoice.concat("\nLiczba pozycji: " + products.keySet().size());
        }
        return allInvoice;
    }
}
