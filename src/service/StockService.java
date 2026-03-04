package service;

import config.AppConfig;
import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
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
    //getPageProduct-feature
    public List<Product> getPageProducts() {
        List<Product> visible = getVisibleProducts();
        int total = visible.size();
        int totalPages = getTotalPages();
        if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;
        int start = (currentPage - 1) * rowsPerPage;
        int end   = Math.min(start + rowsPerPage, total);
        if (start >= total) return new ArrayList<>();
        return visible.subList(start, end);
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
        int tempId = -(pendingInserts.size() + 1);
        product.setProductId(tempId);
        sessionProducts.add(product);
        pendingInserts.add(product);
    }

    public int getTotalProduct(){
        return sessionProducts.size();
    }


    public Product readById(int id) {
        return sessionProducts.stream()
                .filter(p -> p.getProductId() == id && !p.isDeleted())
                .findFirst()
                .orElse(null);

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
        // find the product with id to delete in session
        Optional<Product> opt =
                sessionProducts
                        .stream()
                        .filter(p -> p.getProductId() == id)
                        .findFirst();
        // if product not found return false
        if (opt.isEmpty()) return false;
        // if found and get those product for delete
        Product p = opt.get();
        if (id > 0) productDAO.delete(p.getProductId());
        // remove from session
        sessionProducts.remove(p);
        /* remove the product from pending Insert and Updates
        if the product have added or updated (not yet save to DB)
        * */
        pendingInserts.removeIf(i -> i.getProductId() == id);
        pendingUpdates.removeIf(u -> u.getProductId() == id);

        return true;
    }

    //SearchByName-feature
    public List<Product> searchByName(String keyword) {
        return getVisibleProducts()
                .stream()
                .filter(p -> p.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }


    public boolean save() {
        boolean ok = productDAO.saveToDatabase(pendingInserts, pendingUpdates);
        if (ok) {
            pendingInserts.clear();
            pendingUpdates.clear();
            // Reload from DB to get proper IDs
            loadFromDatabase();
        }
        return ok;
    }

    public List<Product> getUnsavedInserts() { return new ArrayList<>(pendingInserts); }
    public List<Product> getUnsavedUpdates() { return new ArrayList<>(pendingUpdates); }
    public boolean hasUnsavedChanges() {
        return !pendingInserts.isEmpty() || !pendingUpdates.isEmpty() ; }

}
