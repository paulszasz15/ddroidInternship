# Shopping cart project

## Project Description
The project aims to create a program that will compute the totals for a shopping cart of products shipped from different countries, apply various offers and generate a detailed invoice.
Given a pre-defined catalog of products, the user will be able to select any, and as many products and add them to his shopping cart, after, he will proceed to checkout. A detailed invoice will be generated
containing information about the totals and the various offers applied (if possible). Each product is being shipped from a defined country {RO, UK, US} and a shipping fee is applied based on the country 
by multiplying the shipping rate per 0.1kg of the product's weight. <br>

Depending on the user's shopping cart, three offers will be available: 
- Keyboards are 10% off
- Buy 2 Monitors and get a desk lamp at half price
- Buy any 2 items or more and get a maximum of $10 off shipping fees <br>

**VAT** functionality: 
- There is a 19% VAT applied to all products;
- VAT is applied before each discount (if any)
- VAT is not applied to the shipping fee

You are given a static (pre-defined) catalog of products. The user can select multiple items from the catalog of products to add to the shopping cart. Once the shopping cart is ready for checkout, an invoice will be generated containing all details.

**Available catalog of products** : 
| Item name | Item price | Shipped from | Weight |
| -------- | -------- | -------- |  -------- | 
| Mouse | **$10.99** | **RO** | **0.2kg** |
| Keyboard | **$40.99** | **UK** | **0.7kg** |
| Monitor | **$164.99** | **US** | **1.9kg** |
| Webcam | **$84.99** | **RO** | **0.2kg** |
| Headphones | **$59.99** | **US** | **0.6kg** |
| Desk Lamp | **$89.99** | **UK** | **1.3kg** |

**Shipping rates**:
| Country | Rate  | 
| -------- | -------- |
| RO | $1 |
| UK | $2 |
| US | $3 |

***NOTE*** some methods are incorporated in ***details*** block to maintain readability; 


# Usage
1. The program starts with a call to **populateProductCatalog** method and then displaying a list containing the products available for purchase and their price, as follows
    ```
    Mouse - $10.99
    Keyboard - $40.99
    Monitor - $164.99
    Webcam - $84.99
    Headphones - $59.99
    Desk Lamp - $89.99
    ```


2. The user will enter items, one by one (input from keyboard). The shopping cart will be displayed after each addition as follows <br> Example

    | Input | Output |
    | -------- | -------- |
    | Mouse | Mouse x 1 | 
    | Monitor | Mouse x 1<br>Monitor x 1|
    | Monitor | Mouse x 1<br>Monitor x 2|

    The reading is done by an object scanner from the Scanner library. 
    Inside a while loop, after each item read, it is added to the shopping cart and displayed. Everything is achieved inside a try-catch block to filter any invalid inputs (inputs which are not part of the product catalog). <br>
    The program will exit input stage when "**Proceed to checkout**" is introduced after which the invoice will be generated.

   ```java
    while (scanner.hasNext()) {
    String line = scanner.nextLine();
    if(line.equals("Proceed to checkout")) break;
    try {
        Product product = productCatalog.stream().filter(p -> p.getProductName().equals(line)).collect(Collectors.toList()).get(0);
        shoppingCart.addToCart(product);
        shoppingCart.displayShoppingCart();
        }
    catch (Exception ignored){}
    }
    ```

    >Method **`addToCart`**, inside **`ShoppingCart`** object> <br>

    Parsing through the shoppingCart, checking if the product exists; if not we add it to the shopping cart, else we increase the quantity of the item with 1
    ```java
    /**
     * Method to add selected product to shopping cart;
     * @param product - desired product to add to shopping cart
     */
    public void addToCart(Product product) {
        if (this.shoppingCart.containsKey(product)) {
            this.shoppingCart.replace(product, this.shoppingCart.get(product) + 1);
        }
        else {
            this.shoppingCart.put(product, 1);
        }
    }
    ```

    >Method **`displayShoppingCart`**, inside **`ShoppingCart`** object

    ```java
    /**
     * Method to display the items inside the shopping cart;
     */
    public void displayShoppingCart() {
        for (Map.Entry<Product,Integer> entry : shoppingCart.entrySet()) {
            System.out.println(entry.getKey().getProductName() + " x " + entry.getValue());
        }
    }
    ```    

3. Generating the invoice for the shopping cart as follows: <br>
Example
    | Shopping cart contents | Invoice |
    | -------- | -------- |
    | Keyboard x 1 <br> Monitor x 2  | Subtotal: \$370.97 <br/> Shipping: \$128 <br> VAT: \$70.48 <br><br> Discounts: <br>10\% off keyboards: -\$4.09 <br>\$10 off shipping: -\$10  <br><br> Total: $555.36 | 
    | Keyboard x 2 <br> Monitor x 2 <br> Desk Lamp x 1 <br> Mouse x 3 | Subtotal: \$481.73 <br/> Shipping: \$164.00 <br> VAT: \$101.63 <br><br> Discounts: <br>10\% off keyboards: -\$8.198 <br>50% off desk lamp -$44.995 <br> -\$10 off shipping -$10  <br><br> Total: $747.36 | 

In **main**, an Invoice object is created, then a call out to **generateInvoiceWithOffersAndVAT** method and printing totals;
```java
Invoice invoice = new Invoice();
invoice.generateInvoiceWithOffersAndVAT(shoppingCart.getShoppingCart(), shippingRate.getShippingRateCatalog());
System.out.println(invoice.displayTotals());
System.out.println(invoice.displayDiscounts());
System.out.println(invoice.displayTotal());
```

>Method **`generateInvoiceWithOffersAndVAT`**, inside **Invoice** object><br>

Parsing through the shoppingCart, checking for avaiable offers and marking them afterwards calculating **subtotal, shipping, VAT** for each product; <br>
If any offers were applied, discount is calculated for said offers and substracted from subtotals <br>
In the end totals is calculated in $USD;
<details><summary>generateInvoiceWithOffersAndVAT</summary>


```java
/**
     * Method to calculate totals and VAT and apply various special offers (if possible)
     * @param shoppingCart - containing the items introduced by the user in the shopping cart
     * @param shippingRateCatalog - containing each country's shipping rate 
     */
    public void generateInvoiceWithOffersAndVAT(HashMap<Product, Integer> shoppingCart, HashMap<String, Integer> shippingRateCatalog) {
        int nrOfDeskLampOffers = 0;
        double vat;


        
        for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
            if (entry.getKey().getProductName().equals("Keyboard")) {
                specialOffers.replace("KeyboardOffer", true);
            }
            if (entry.getKey().getProductName().equals("Monitor") && entry.getValue() >= 2) {
                    specialOffers.replace("DeskLampOffer", true);
                    nrOfDeskLampOffers = entry.getValue() / 2;
            }

            this.subtotal += (entry.getKey().getProductPrice() * entry.getValue());
            this.shipping += (entry.getKey().getProductWeight() * 10 * shippingRateCatalog.get(entry.getKey().getShippingCountry())) * entry.getValue();

            vat = entry.getKey().getProductPrice() * 0.19;
            this.VAT += vat * entry.getValue();

        }

        if (specialOffers.get("KeyboardOffer").equals(true)) {
            Entry<Product, Integer> entry = shoppingCart.entrySet().stream().filter(key -> key.getKey().getProductName().equals("Keyboard")).collect(Collectors.toList()).get(0);
            this.keyboardDiscount = entry.getKey().getProductPrice() * 0.1 * entry.getValue();
            this.subtotal -= keyboardDiscount;
        }


        if (specialOffers.get("DeskLampOffer").equals(true)) {
            Entry<Product, Integer> entry = shoppingCart.entrySet().stream().filter(key -> key.getKey().getProductName().equals("Desk Lamp")).collect(Collectors.toList()).get(0);
            this.deskLampDiscount = entry.getKey().getProductPrice() * 0.5 * nrOfDeskLampOffers;
            this.subtotal -= deskLampDiscount;


        }


        if (shoppingCart.size() >= 2) {
            this.shipping -= 10;
            specialOffers.replace("ShippingOffer", true);
        }

        this.total = this.subtotal + this.shipping + this.VAT;
    }
```

</details>


# Other
Example on how shipping and VAT are calculated
| Item name | Item price | Shipped from | Weight | Shipping Rate | Shipping Cost | VAT |
| -------- | -------- | -------- |  -------- | -------- | -------- | -------- | 
| Mouse | $10.99 | RO | 0.2kg | $1 | $2 | $2.08 |
| Keyboard | $40.99 | UK | 0.7kg | $2 | $14 | $7.78 |
| Monitor | $164.99 | US | 1.9kg | $3 | $57 | $31.34 |
| Webcam | $84.99 | RO | 0.2kg | $1 | $2 | $16.14 |
| Headphones | $59.99 | US | 0.6kg | $3 | $18 | $11.39 |
| Desk Lamp | $89.99 | UK | 1.3kg | $2 | $26 | $17.09


### Libraries 
* The list below contains all the libraries necessary for the program.

    ```java
    import java.util.ArrayList;
    import java.util.Scanner;
    import java.util.stream.Collectors;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.Map.Entry;
    import java.util.stream.Collectors;
    ```

* For the testing libraries:

    ```java
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.BeforeAll;
    import org.junit.jupiter.api.Test;
    ```

### Testing
Test objects are created and given certain values for the purpose of testing. Tests were applied to verify if the totals calculated match the expected outcome. <br>
Testing has been achieved with JUnit5 and specified libraries above. <br>
***Values printed in console have been reduced to 2 decimals each, rounding them up***
<details><summary>Totals tests without VAT and offers</summary>

```java
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
```
</details>


<details><summary>Totals tests with VAT and offers</summary>

```java
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
```
</details>
