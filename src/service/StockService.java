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
    public List<Integer> getProductsID(){
        List<Integer> productsID = new ArrayList<>();
        for(Product product : sessionProducts){
            productsID.add(product.getProductId());
        }
        return productsID;
    }
}
