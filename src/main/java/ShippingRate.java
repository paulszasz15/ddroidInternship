import java.util.HashMap;

public class ShippingRate {
    private  final HashMap<String, Integer> shippingRateCatalog = new HashMap<>();
    private static ShippingRate shippingRate = null;

    private ShippingRate() {
    }

    /**
     * Method return an instance of singleton class shipping rate
     * @return - instance of the class
     */
    public static ShippingRate getInstance()
    {
        if (shippingRate == null)
            shippingRate = new ShippingRate();

        return shippingRate;
    }


    /**
     * Method to populate shipping rate catalog
     */
    public  void populateShippingRateCatalog() {
        shippingRateCatalog.put("RO", 1);
        shippingRateCatalog.put("UK", 2);
        shippingRateCatalog.put("US", 3);
    }

    /**
     * Method to obtain shipping rate catalog
     * @return - shipping rate catalog
     */
    public HashMap<String, Integer> getShippingRateCatalog() {
        return shippingRateCatalog;
    }
}
