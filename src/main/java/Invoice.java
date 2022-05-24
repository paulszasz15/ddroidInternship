import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Invoice {
    private Double subtotal;
    private Double shipping;
    private Double total;
    private Double VAT;
    private Double keyboardDiscount;
    private Double deskLampDiscount;
    HashMap<String, Boolean> specialOffers;


    public Invoice() {
        specialOffers = new HashMap<>();
        specialOffers.put("KeyboardOffer", false);
        specialOffers.put("DeskLampOffer", false);
        specialOffers.put("ShippingOffer", false);
        this.subtotal = 0d;
        this.shipping = 0d;
        this.total = 0d;
        this.VAT = 0d;
        this.keyboardDiscount = 0d;
        this.deskLampDiscount = 0d;
    }

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



    /**
     * Method to calculate subtotal, total and shipping for the invoice and generate it without special offers and VAT
     * @param shoppingCart - final version of shopping cart after proceeding to checkout;
     * @param shippingRateCatalog - catalog of each country's shipping rate
     */
    public void generateInvoiceWithoutOffersAndVAT(HashMap<Product, Integer> shoppingCart, HashMap<String, Integer> shippingRateCatalog) {
        for (Map.Entry<Product, Integer> entry : shoppingCart.entrySet()) {
            this.subtotal += (entry.getKey().getProductPrice() * entry.getValue());
            this.shipping += (entry.getKey().getProductWeight() * 10 * shippingRateCatalog.get(entry.getKey().getShippingCountry())) * entry.getValue();
        }
        this.total = this.subtotal + this.shipping;
    }


    /**
     * Method to display subtotals, shipping and vat
     * @return string output
     */
    public String displayTotals() {
        return String.format("""
                        Invoice
                        Subtotal: $%.02f\s
                        Shipping: $%.02f\s
                        VAT: $%.02f\s
                        """, this.subtotal, this.shipping, this.VAT
                );
    }

    /**
     * Method to display discounts applied
     * @return string output
     */
    public String displayDiscounts() {
        String discountOutput = "Discounts:";
        if (this.specialOffers.get("KeyboardOffer").equals(true))  discountOutput += "\n10% off keyboards: -$" + keyboardDiscount;
        if (this.specialOffers.get("DeskLampOffer").equals(true))  discountOutput += "\n50% off desk lamp -$" + deskLampDiscount;
        if (this.specialOffers.get("ShippingOffer").equals(true))  discountOutput += "\n$10 off shipping -$10\n";

        return discountOutput;
    }


    /**
     * Method to display total $
     * @return string output
     */
    public String displayTotal() {
        return String.format("""
                            Total: $%.02f
                            """, this.total);
    }

    public Double getSubtotal() {
        return this.subtotal;
    }

    public Double getShipping() {
        return this.shipping;
    }

    public Double getTotal() {
        return this.total;
    }

    public Double getVAT() {
        return this.VAT;
    }
}
