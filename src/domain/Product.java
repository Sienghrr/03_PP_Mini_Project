package domain;

import java.time.LocalDate;

public class Product {
    private int productId;
    private String productName;
    private double unitPrice;
    private int quantity;
    private LocalDate importedDate;
    private boolean deleted;

    public Product() {}

    public Product(int productId, String productName, double unitPrice, int quantity, LocalDate importedDate) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.importedDate = importedDate;
        this.deleted = false;
    }

    public Product(String productName, double unitPrice, int quantity, LocalDate importedDate) {
        this(0, productName, unitPrice, quantity, importedDate);
    }

    // getter & setter
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(LocalDate importedDate) {
        this.importedDate = importedDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
