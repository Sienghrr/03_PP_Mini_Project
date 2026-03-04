package service;

import com.sun.source.tree.BreakTree;
import config.AppConfig;
import dao.BackupDAO;
import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
import domain.BackupVersion;
import domain.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockService {

    public StockService() {
        this.rowsPerPage = AppConfig.getRowsPerPage();
    }
    public void setRowsPerPage(int rows) {
        this.rowsPerPage = Math.max(1, rows);
        this.currentPage = 1;
        AppConfig.saveRowsPerPage(this.rowsPerPage);
    }
    private final ProductDAO productDAO = new ProductDAOImpl();
    private final BackupDAO backupDAO  = new BackupDAO();

    // In-memory session store (loaded from DB + pending changes)
    private List<Product> sessionProducts = new ArrayList<>();


    //pending change (only apply to db when call save change)
    private final List<Product>  pendingInserts = new ArrayList<>();
    private final List<Product>  pendingUpdates = new ArrayList<>();

    // Pagination state
    private int rowsPerPage;
    private int currentPage = 1;

    public void loadFromDatabase() {
        sessionProducts = new ArrayList<>(productDAO.findAll());
        pendingInserts.clear();
        pendingUpdates.clear();
    }

    public List<Product> getVisibleProducts() {
        return sessionProducts
                .stream()
                .filter(p -> !p.isDeleted() && p.getProductId() > 0 )
                .collect(Collectors.toList());
    }

    public List<Product> getPageProducts() {
       return null;
    }

    public int getTotalPages() {
        int total = getVisibleProducts().size();
        return (int) Math.ceil((double) total / rowsPerPage);
    }

    public int getCurrentPage()    { return currentPage; }
    public void goToFirstPage()    { currentPage = 1; }
    public void goToLastPage()     { currentPage = Math.max(1, getTotalPages()); }
    public void goToNextPage()     { if (currentPage < getTotalPages()) currentPage++; }
    public void goToPreviousPage() { if (currentPage > 1) currentPage--; }
    public void goToPage(int page) {
        int total = getTotalPages();
        if (page < 1) page = 1;
        if (page > total) page = total;
        currentPage = page;
    }


    public void writeProduct(Product product) {

    }

    public int getTotalProduct(){
        return sessionProducts.size();
    }


    public Product readById(int id) {
        return null;
    }


    public boolean updateProduct(int id, String name, double price, int qty, LocalDate date) {
        Optional<Product> insert = pendingInserts.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst();
        if (insert.isPresent()) {
            Product p = insert.get();
            p.setProductName(name);
            p.setUnitPrice(price);
            p.setQuantity(qty);
            p.setImportedDate(date);
            return true;
        }

        boolean exists = sessionProducts.stream().anyMatch(p -> p.getProductId() == id);
        if (!exists) return false;

        pendingUpdates.removeIf(u -> u.getProductId() == id);

        Product original = sessionProducts.stream()
                .filter(p -> p.getProductId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Product not found: " + id));

        Product patched = new Product(name, price, qty, date);
        patched.setProductId(original.getProductId());
        pendingUpdates.add(patched);
        return true;
    }

    public boolean deleteProduct(int id) {
        return true;
    }


    public List<Product> searchByName(String keyword) {
        List<Product> visible = getVisibleProducts();
        int total = visible.size();
        int totalPages = getTotalPages();
        if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;
        int start = (currentPage - 1) * rowsPerPage;
        int end   = Math.min(start + rowsPerPage, total);
        if (start >= total) return new ArrayList<>();
        return visible.subList(start, end);
    }


    public boolean save() {
        return false;
    }

    public List<Product> getUnsavedInserts() { return new ArrayList<>(pendingInserts); }
    public List<Product> getUnsavedUpdates() { return new ArrayList<>(pendingUpdates); }
    public boolean hasUnsavedChanges() {
        return !pendingInserts.isEmpty() || !pendingUpdates.isEmpty() ; }


    public BackupVersion createBackup(String name, String description) {
        return backupDAO.createBackup(name, description);
    }

    public List<BackupVersion> getAllBackupVersions() {
        return backupDAO.getAllVersions();
    }

    public boolean restoreVersion(int versionId) {
        boolean ok = backupDAO.restoreVersion(versionId);
        if (ok) loadFromDatabase();
        return ok;
    }

    public boolean deleteBackupVersion(int versionId) {
        return backupDAO.deleteVersion(versionId);
    }

    public List<Product> previewBackup(int versionId) {
        return backupDAO.getBackupProducts(versionId);
    }
}
