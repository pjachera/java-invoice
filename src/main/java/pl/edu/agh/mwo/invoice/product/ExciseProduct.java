package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;

public class ExciseProduct extends Product {
    private final BigDecimal excise;

    public ExciseProduct(String name, BigDecimal price, BigDecimal tax, BigDecimal excise) {
        super(name, price, tax);
        this.excise = excise;
    }

    public BigDecimal getExcise() {
        return excise;
    }

    @Override
    public BigDecimal getPriceWithTax() {

        return this.getPrice().multiply(this.getTaxPercent()).add(this.getPrice()).add(getExcise());
    }

}
