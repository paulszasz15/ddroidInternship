import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class InvoiceTest {
    static Invoice invoiceTest;
    static ShoppingCart shoppingCart;
    static ShippingRate shippingRate;

    //Testing for Mouse x 1 ; Monitor x 2
    @BeforeAll
    static void setUpBefore() {
        invoiceTest = new Invoice();
        shoppingCart = new ShoppingCart();
        shippingRate = ShippingRate.getInstance();

        shoppingCart.addToCart(new Product("Mouse", 10.99, "RO", 0.2));
        shoppingCart.addToCart(new Product("Monitor", 164.99, "US", 1.9));
        shoppingCart.addToCart(new Product("Monitor", 164.99, "US", 1.9));
        shippingRate.populateShippingRateCatalog();
        invoiceTest.generateInvoiceWithoutOffersAndVAT(shoppingCart.getShoppingCart(), shippingRate.getShippingRateCatalog());
    }

    @Test
    void testSubtotals() {
        Assertions.assertEquals(340.97, invoiceTest.getSubtotal());
    }

    @Test
    void testShipping() {
        Assertions.assertEquals(116, invoiceTest.getShipping());
    }

    @Test
    void testTotals() {

        Assertions.assertEquals(456.97, invoiceTest.getTotal());
    }
}