import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class InvoiceTestOffersVAT {
    static Invoice invoiceTest;
    static ShoppingCart shoppingCart;
    static ShippingRate shippingRate;

    //Testing for Mouse x 1 ; Monitor x 2 ; Keyboard x 2; Desk Lamp x 1
    @BeforeAll
    static void setUpBefore() {
        invoiceTest = new Invoice();
        shoppingCart = new ShoppingCart();
        shippingRate = ShippingRate.getInstance();

        shoppingCart.getShoppingCart().put(new Product("Monitor", 164.99, "US", 1.9),2);
        shoppingCart.getShoppingCart().put(new Product("Keyboard", 40.99, "UK", 0.7),2);
        shoppingCart.getShoppingCart().put(new Product("Mouse", 10.99, "RO", 0.2),1);
        shoppingCart.getShoppingCart().put(new Product("Desk Lamp", 89.99, "UK", 1.3),1);


        shippingRate.populateShippingRateCatalog();
        invoiceTest.generateInvoiceWithOffersAndVAT(shoppingCart.getShoppingCart(), shippingRate.getShippingRateCatalog());
    }

    @Test
    void testSubtotals() {
        Assertions.assertEquals(459.74700000000007, invoiceTest.getSubtotal());
    }

    @Test
    void testShipping() {
        Assertions.assertEquals(160.00, invoiceTest.getShipping());
    }

    @Test
    void testTotals() {

        Assertions.assertEquals(717.2056000000001, invoiceTest.getTotal());
    }

    @Test
    void testVAT(){
        Assertions.assertEquals(97.4586, invoiceTest.getVAT());
    }
}