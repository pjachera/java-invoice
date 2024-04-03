package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class BottleOfWine extends ExciseProduct {
    public BottleOfWine(String name, BigDecimal price, BigDecimal tax, BigDecimal excise) {
        super(name, price, tax, excise);
    }

}
