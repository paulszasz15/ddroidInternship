import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final HashMap<Product, Integer> shoppingCart = new HashMap<>();

    /**
     * Method to display the items inside the shopping cart;
     */
    public void displayShoppingCart() {
        for (Map.Entry<Product,Integer> entry : shoppingCart.entrySet()) {
            System.out.println(entry.getKey().getProductName() + " x " + entry.getValue());
        }
    }


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


    public HashMap<Product, Integer> getShoppingCart() {
        return shoppingCart;
    }


}
