import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> productCatalog = populateProductCatalog();
        ShippingRate shippingRate = ShippingRate.getInstance();
        shippingRate.populateShippingRateCatalog();


        // Displaying the catalog of products @name and @price of each product;
        for (Product product : productCatalog) {
            System.out.println(product.getProductName() + " - " + "$" + product.getProductPrice());
        }


        Scanner scanner = new Scanner(System.in);
        ShoppingCart shoppingCart = new ShoppingCart();
        System.out.println("\nPlease enter the desired products into the shopping cart: ");

        // Adding products by their productName from keyboard input until "Proceed to checkout" is entered; Ignoring any other inputs except productNames
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


        System.out.println("Shopping cart contents");
        shoppingCart.displayShoppingCart();
        System.out.println();


        Invoice invoice = new Invoice();
        invoice.generateInvoiceWithOffersAndVAT(shoppingCart.getShoppingCart(), shippingRate.getShippingRateCatalog());




        System.out.println(invoice.displayTotals());
        System.out.println(invoice.displayDiscounts());
        System.out.println(invoice.displayTotal());


    }


    /**
     * Method to populate the product catalog;
     * @return productCatalog
    */
    private static ArrayList<Product> populateProductCatalog() {
        ArrayList<Product> productCatalog = new ArrayList<>();

        Product productMouse = new Product("Mouse", 10.99, "RO", 0.2);
        Product productKeyboard = new Product("Keyboard", 40.99, "UK", 0.7);
        Product productMonitor = new Product("Monitor", 164.99, "US", 1.9);
        Product productWebcam = new Product("Webcam", 84.99, "RO", 0.2);
        Product productHeadphones = new Product("Headphones", 59.99, "US", 0.6);
        Product productDeskLamp = new Product("Desk Lamp", 89.99, "UK", 1.3);

        productCatalog.add(productMouse);
        productCatalog.add(productKeyboard);
        productCatalog.add(productMonitor);
        productCatalog.add(productWebcam);
        productCatalog.add(productHeadphones);
        productCatalog.add(productDeskLamp);

        return  productCatalog;
    }

}
