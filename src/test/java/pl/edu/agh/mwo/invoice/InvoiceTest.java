package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.Invoice;
import pl.edu.agh.mwo.invoice.product.*;

public class InvoiceTest {
    private Invoice invoice;

    @Before
    public void createEmptyInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoiceHasEmptySubtotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTaxAmount() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testEmptyInvoiceHasEmptyTotal() {
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithTwoDifferentProducts() {
        Product onions = new TaxFreeProduct("Warzywa", new BigDecimal("10"));
        Product apples = new TaxFreeProduct("Owoce", new BigDecimal("10"));
        invoice.addProduct(onions);
        invoice.addProduct(apples);
        Assert.assertThat(new BigDecimal("20"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceSubtotalWithManySameProducts() {
        Product onions = new TaxFreeProduct("Warzywa", BigDecimal.valueOf(10));
        invoice.addProduct(onions, 100);
        Assert.assertThat(new BigDecimal("1000"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasTheSameSubtotalAndTotalIfTaxIsZero() {
        Product taxFreeProduct = new TaxFreeProduct("Warzywa", new BigDecimal("199.99"));
        invoice.addProduct(taxFreeProduct);
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasProperSubtotalForManyProducts() {
        invoice.addProduct(new TaxFreeProduct("Owoce", new BigDecimal("200")));
        invoice.addProduct(new DairyProduct("Maslanka", new BigDecimal("100")));
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("310"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasProperTaxValueForManyProduct() {
        // tax: 0
        invoice.addProduct(new TaxFreeProduct("Pampersy", new BigDecimal("200")));
        // tax: 8
        invoice.addProduct(new DairyProduct("Kefir", new BigDecimal("100")));
        // tax: 2.30
        invoice.addProduct(new OtherProduct("Piwko", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("10.30"), Matchers.comparesEqualTo(invoice.getTaxTotal()));
    }

    @Test
    public void testInvoiceHasProperTotalValueForManyProduct() {
        // price with tax: 200
        invoice.addProduct(new TaxFreeProduct("Maskotki", new BigDecimal("200")));
        // price with tax: 108
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("100")));
        // price with tax: 12.30
        invoice.addProduct(new OtherProduct("Chipsy", new BigDecimal("10")));
        Assert.assertThat(new BigDecimal("320.30"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }

    @Test
    public void testInvoiceHasPropoerSubtotalWithQuantityMoreThanOne() {
        // 2x kubek - price: 10
        invoice.addProduct(new TaxFreeProduct("Kubek", new BigDecimal("5")), 2);
        // 3x kozi serek - price: 30
        invoice.addProduct(new DairyProduct("Kozi Serek", new BigDecimal("10")), 3);
        // 1000x pinezka - price: 10
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("50"), Matchers.comparesEqualTo(invoice.getNetTotal()));
    }

    @Test
    public void testInvoiceHasPropoerTotalWithQuantityMoreThanOne() {
        // 2x chleb - price with tax: 10
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5")), 2);
        // 3x chedar - price with tax: 32.40
        invoice.addProduct(new DairyProduct("Chedar", new BigDecimal("10")), 3);
        // 1000x pinezka - price with tax: 12.30
        invoice.addProduct(new OtherProduct("Pinezka", new BigDecimal("0.01")), 1000);
        Assert.assertThat(new BigDecimal("54.70"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }


    @Test
    public void testInvoiceHaveANumber() {
        Assert.assertNotNull(invoice.getNumber());
    }

    @Test
    public void testInvoiceNumberIsGreaterThanZero() {
        Assert.assertTrue(invoice.getNumber() > 0);
    }

    @Test
    public void testFirstInvoiceNumberIsLessThanSecond() {
        Assert.assertTrue(invoice.getNumber() < new Invoice().getNumber());
    }

    @Test
    public void testPrintEmptyInvoice() {
        Assert.assertEquals(invoice.print(), "");
    }

    @Test
    public void testPrintProductsFromInvoice() {
        invoice.addProduct(new DairyProduct("Chleb", new BigDecimal("4.5")));
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 2);
        invoice.addProduct(new DairyProduct("Bulka", new BigDecimal("1")), 2);
        invoice.addProduct(new OtherProduct("Piwo", new BigDecimal("3")), 8);

        Assert.assertEquals(invoice.print(), "Numer faktury:" + invoice.getNumber() + "\n" +
                "Nazwa produktu:Chleb Liczba sztuk:1 Cena:4.5zł\n" +
                "Nazwa produktu:Maslo Liczba sztuk:2 Cena:7zł\n" +
                "Nazwa produktu:Bulka Liczba sztuk:2 Cena:1zł\n" +
                "Nazwa produktu:Piwo Liczba sztuk:8 Cena:3zł\n" +
                "Liczba pozycji: 4");
    }

    @Test
    public void testPrintAddTwoSameProducts() {
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")));
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")));

        Assert.assertEquals(invoice.print(), "Numer faktury:" + invoice.getNumber() + "\n" +
                "Nazwa produktu:Maslo Liczba sztuk:2 Cena:7zł\n" +
                "Liczba pozycji: 1");

    }

    @Test
    public void testPrintAddFourSameProducts() {
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")));
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")));
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")));
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")));

        Assert.assertEquals(invoice.print(), "Numer faktury:" + invoice.getNumber() + "\n" +
                "Nazwa produktu:Maslo Liczba sztuk:4 Cena:7zł\n" +
                "Liczba pozycji: 1");

    }

    @Test
    public void testPrintAddTwoSameProductsWithQuantity() {
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 8);
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 5);

        Assert.assertEquals(invoice.print(), "Numer faktury:" + invoice.getNumber() + "\n" +
                "Nazwa produktu:Maslo Liczba sztuk:13 Cena:7zł\n" +
                "Liczba pozycji: 1");

    }

    @Test
    public void testPrintAddFiveSameProductsWithQuantity() {
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 8);
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 5);
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 5);
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 5);
        invoice.addProduct(new DairyProduct("Maslo", new BigDecimal("7")), 5);

        Assert.assertEquals(invoice.print(), "Numer faktury:" + invoice.getNumber() + "\n" +
                "Nazwa produktu:Maslo Liczba sztuk:28 Cena:7zł\n" +
                "Liczba pozycji: 1");

    }

    @Test
    public void testInvoiceWithTwoProductsAndExcise() {
        invoice.addProduct(new ExciseProduct("Vodka", new BigDecimal("20"),
                new BigDecimal("0.23"), new BigDecimal("5.56")));                //30,16
        invoice.addProduct(new ExciseProduct("Vodka", new BigDecimal("20"),
                new BigDecimal("0.23"), new BigDecimal("5.56")));                 //30,16

        Assert.assertThat(new BigDecimal("60.32"), Matchers.comparesEqualTo(invoice.getGrossTotal()));

    }

    @Test
    public void testInvoiceWithTwoProductsAndExciseWithQuantity() {
        invoice.addProduct(new ExciseProduct("Vodka", new BigDecimal("20"),
                new BigDecimal("0.23"), new BigDecimal("5.56")), 5);    //30,16 x 5 = 150,8
        invoice.addProduct(new ExciseProduct("Vodka", new BigDecimal("20"),
                new BigDecimal("0.23"), new BigDecimal("5.56")), 5);    //30,16 x 5 = 150,8

        Assert.assertThat(new BigDecimal("301.6"), Matchers.comparesEqualTo(invoice.getGrossTotal()));

    }

    @Test
    public void testInvoiceWithFuelCanisterProductInMotherInLawDay() {        // Five of March the test not Pass
        invoice.addProduct(new FuelCanisterProduct("Benzyna", new BigDecimal("27.5"), //33.825 + 5.56 = 39.385
                new BigDecimal("0.23"), new BigDecimal("5.56")), 1);
        Assert.assertThat(new BigDecimal("39.385"), Matchers.comparesEqualTo(invoice.getGrossTotal()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithZeroQuantity() {
        invoice.addProduct(new TaxFreeProduct("Tablet", new BigDecimal("1678")), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvoiceWithNegativeQuantity() {
        invoice.addProduct(new DairyProduct("Zsiadle mleko", new BigDecimal("5.55")), -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullProduct() {
        invoice.addProduct(null);
    }

}
