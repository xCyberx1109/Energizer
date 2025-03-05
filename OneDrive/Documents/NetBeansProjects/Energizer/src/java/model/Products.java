
package model;

public class Products {
    private String productName, category,images,description;
    private Double price;
    private int StockQuantity,ProductID;

    public Products() {
    }

    public Products(String productName, String category, String images, String description, Double price, int StockQuantity, int ProductID) {
        this.productName = productName;
        this.category = category;
        this.images = images;
        this.description = description;
        this.price = price;
        this.StockQuantity = StockQuantity;
        this.ProductID = ProductID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return StockQuantity;
    }

    public void setStockQuantity(int StockQuantity) {
        this.StockQuantity = StockQuantity;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int id) {
        this.ProductID = ProductID;
    }


}
