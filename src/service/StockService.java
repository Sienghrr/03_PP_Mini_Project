package service;

import dao.BackupDAO;
import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
import domain.Product;

import java.util.ArrayList;
import java.util.List;

public class StockService {
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final BackupDAO backupDAO  = new BackupDAO();

    // In-memory session store (loaded from DB + pending changes)
    private List<Product> sessionProducts = new ArrayList<>();

    // Unsaved tracking
    private final List<Product>  pendingInserts = new ArrayList<>();
    private final List<Product>  pendingUpdates = new ArrayList<>();
    private final List<Integer>  pendingDeletes = new ArrayList<>();
    private boolean hasUnsavedChanges = false;

    public void loadFromDatabase() {
        sessionProducts = new ArrayList<>(productDAO.findAll());
        pendingInserts.clear();
        pendingUpdates.clear();
        pendingDeletes.clear();
        hasUnsavedChanges = false;
    }
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        String name = product.getProductName();
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getUnitPrice()<=0 ){
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        if (product.getQuantity()<0){
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (product.getImportedDate() == null) {
            throw new IllegalArgumentException("Imported date cannot be null");
        }
    sessionProducts.add(product);
    pendingInserts.add(product);
    hasUnsavedChanges = true;
    }

    public Product getProductById(int id) {
        for (Product product : sessionProducts) {
            if (product.getProductId() == id) {
                return product;
            }
        }
        return null;
    }

}
