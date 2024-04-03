package pl.edu.agh.mwo.invoice.product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class FuelCanisterProduct extends ExciseProduct {
    private static final int MOTHER_DAY = 5;

    public FuelCanisterProduct(String name, BigDecimal price, BigDecimal tax, BigDecimal excise) {
        super(name, price, tax, excise);
    }

    @Override
    public BigDecimal getPriceWithTax() {

        BigDecimal excise = null;
        LocalDate localDate = LocalDate.now();
        if ((localDate.getDayOfMonth() == MOTHER_DAY) && (localDate.getMonth() == Month.MARCH)) {
            excise = BigDecimal.ZERO;
        } else {
            excise = this.getExcise();
        }

        return this.getPrice().multiply(this.getTaxPercent()).add(this.getPrice()).add(excise);
    }
}
