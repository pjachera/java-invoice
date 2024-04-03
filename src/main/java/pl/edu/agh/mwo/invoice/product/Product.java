package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax) {
        if (name == null || name.equals("") || price == null
                || tax == null || tax.compareTo(new BigDecimal(0)) < 0
                || price.compareTo(new BigDecimal(0)) < 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.price = price;
        this.taxPercent = tax;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public BigDecimal getPriceWithTax() {
        return price.multiply(taxPercent).add(price);
    }


    public boolean equals(Product product) {
        if (this.getName().equals(product.name) && this.getPrice().equals(product.getPrice())
            && this.getTaxPercent().equals(product.getTaxPercent())) {
            return true;
        }
        return false;
    }
}
//    public boolean compareTo(Product anotherProduct) {
//        return compare(this, anotherProduct);
//    }
//
//    public static boolean compare (Product x, Product y) {
//        if(x.getName().equals(y.name) && x.getPrice().equals(y.getPrice())
//                && x.getTaxPercent().equals(y.getTaxPercent())){
//            return true;
//        }
//        return false;
//    }

