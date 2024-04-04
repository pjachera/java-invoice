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
        if (product == null) {
            throw new IllegalArgumentException();
        } else {
            int quantityCount = 0;
            for (Product product1 : products.keySet()) {
                if (product.equals(product1)) {
                    quantityCount = +products.get(product1);
                    products.remove(product1);
                }
            }
            if (quantityCount == 0) {
                products.put(product, 1);
            } else {
                products.put(product, quantityCount + 1);
            }
        }
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        } else {
            int quantityCount = 0;
            for (Product product1 : products.keySet()) {
                if (product.equals(product1)) {
                    quantityCount = +products.get(product1);
                    products.remove(product1);
                }
            }
            if (quantityCount == 0) {
                products.put(product, quantity);
            } else {
                products.put(product, quantity + quantityCount);
            }
        }
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

                allInvoice = allInvoice.concat("\nNazwa produktu:" + product.getName()
                        + " Liczba sztuk:" + products.get(product).intValue()
                        + " Cena:" + product.getPrice() + "zł");

            }
            allInvoice = allInvoice.concat("\nLiczba pozycji: " + products.keySet().size());
        }
        return allInvoice;
    }
}

