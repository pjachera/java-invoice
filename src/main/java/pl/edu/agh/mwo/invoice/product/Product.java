package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public abstract class Product {
    private final String name;

    private final BigDecimal price;

    private final BigDecimal taxPercent;

    protected Product(String name, BigDecimal price, BigDecimal tax){

        if( (name == null) || (name.isEmpty()) )
         throw new IllegalArgumentException("Product name cannot be null");

        if( (price == null) || (price.intValue() < 0) )
            throw new IllegalArgumentException("Price cannot be null");

        if( (tax == null) || (tax.intValue() < 0) )
            throw new IllegalArgumentException("Tax cannot be null");

        this.name = name;
        this.price = price;
        this.taxPercent = tax;

    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getTaxPercent() {
        return this.taxPercent;
    }

    public BigDecimal getPriceWithTax() {
        return price.add(price.multiply(taxPercent));
    }
}
