public class Product {
    private String productName;
    private Double productPrice;
    private String shippingCountry;
    private Double productWeight;

    public Product(String productName, Double productPrice, String shippingCountry, Double productWeight) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.shippingCountry = shippingCountry;
        this.productWeight = productWeight;
    }

    public String getProductName() {
        return productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public Double getProductWeight() {
        return productWeight;
    }
}
