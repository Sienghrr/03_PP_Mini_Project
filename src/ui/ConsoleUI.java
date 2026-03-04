package ui;

import domain.Product;
import service.StockService;
import utils.TableHelper;
import utils.Validate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static utils.Utils.*;

public class ConsoleUI {

    private final StockService service = new StockService();

    public void start(){

        System.out.println(CYAN + BOLD);
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 STOCK MANAGEMENT SYSTEM                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝" + RESET);
        service.loadFromDatabase();
        System.out.println(GREEN + "✔ Connected to database and loaded products." + RESET);
        System.out.println();
        TableHelper.displayTable(service.getPageProducts(),service.getCurrentPage(),service.getTotalPages());

        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = Validate.validate(getString("=> Choose option : "));
            switch (choice.toLowerCase()) {
                case "w"  -> writeProduct();
                case "r"  -> readProductById();
                case "u"  -> updateProduct();
                case "d"  -> deleteProduct();
                case "s"  -> searchByName();
                case "se"  -> setRowsPerPage();
                case "sa"  -> saveChanges();
                case "un"  -> viewUnsavedChanges();
                case "pa" -> paginationMenu();
                case "e"  -> {
                    running = exitApp();
                    System.exit(0);
                }
                default   -> System.out.println(RED + "Invalid option. Please try again." + RESET);
            }
        }
    }

    private void showMainMenu() {
        System.out.println();
        System.out.println(CYAN + "┌─────────────────────────────────────────┐");
        System.out.println("│           MAIN MENU                     │");
        System.out.println("├─────────────────────────────────────────┤");
        System.out.println("│  W)  Write (Add) Product                │");
        System.out.println("│  R)  Read Product by ID                 │");
        System.out.println("│  U)  Update Product                     │");
        System.out.println("│  D)  Delete Product                     │");
        System.out.println("│  S)  Search by Name                     │");
        System.out.println("│  Se)  Set Rows per Page                 │");
        System.out.println("│  sa)  Save Changes to Database          │");
        System.out.println("│  Un)  View Unsaved Changes              │");
        System.out.println("│  pa)  Pagination                        │");
        System.out.println("│  ba) Backup (create version)            │");
        System.out.println("│  re) Restore (select version)           │");
        System.out.println("│  e)  Exit                               │");
        System.out.println("└─────────────────────────────────────────┘" + RESET);
        if (service.hasUnsavedChanges()) {
            System.out.println(YELLOW + "  ⚠  You have unsaved changes!" + RESET);
        }
    }

    private void writeProduct() {
        System.out.println(BLUE + "\n── Add New Product ──────────────────────────" + RESET);
        println(" ID " + (service.getTotalProduct() + 1));
        String name     = Validate.validateProductName(getString(" Enter Product Name : "));
        double price    =  getDouble(" Enter Unit price : ");
        int    qty      = getInt(" Enter Quantity : ");
        Product p = new Product(name, price, qty, LocalDate.now());
        service.writeProduct(p);
        System.out.println(GREEN + "  ✔ Product added to session (not yet saved to DB)." + RESET);
    }

    private void readProductById() {
        int id = getInt(" Enter product ID : ");
        Product p = service.readById(id);
        if (Objects.isNull(p))
            println(RED + "  ✘ Product not found." + RESET);
        else
           TableHelper.displayTable(p);

    }

    private void updateProduct() {
        int id = getInt(" Enter product ID to update : ");
        Product p = service.readById(id);
        if (Objects.isNull(p)) {
            System.out.println(RED + "  ✘ Product not found." + RESET); return; }

        println("  ( Press Enter to keep current value )");
        String name    = Validate.validateProductName(promptOrDefault(" New Product Name ", p.getProductName()));
        double price   = promptDoubleOrDefault(" New unit price ", p.getUnitPrice());
        int qty        = promptIntOrDefault(" New quantity ", p.getQuantity());
        boolean ok = service.updateProduct(id, name, price, qty, p.getImportedDate());
        println(ok ? GREEN + "  ✔ Product updated in session." + RESET
                : RED + "  ✘ Update failed." + RESET);
    }

    private void deleteProduct() {
        int id = getInt("  Enter product ID to delete : ");
        String confirm = Validate.validate(getString("  Are you sure? (y/n)"));
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("  Cancelled."); return; }
        boolean ok = service.deleteProduct(id);
        System.out.println(ok ? GREEN + "  ✔ Product is deleted ." + RESET
                : RED + "  ✘ Product not found." + RESET);
    }

    private void searchByName() {
        String kw = Validate.validate(getString("Enter key to search : "));
        List<Product> results = service.searchByName(kw);
        if (results.isEmpty()) println( "No results found for: " + kw);
        else TableHelper.displayTable(results);
        System.out.println("  " + results.size() + " result(s) found.");
    }

    private void setRowsPerPage() {
        int rows = (int) getNumber(" Enter number of rows per page : ");
        service.setRowsPerPage(rows);
        TableHelper.displayTable(service.getPageProducts(),service.getCurrentPage(),service.getTotalPages());
        System.out.println(GREEN + "  ✔ Rows per page set to " + rows + "." + RESET);
    }

    private void saveChanges() {
        if (!service.hasUnsavedChanges()) {
            System.out.println(YELLOW + "  No unsaved changes." + RESET);
            return;
        }
        System.out.println("  Inserts pending : " + service.getUnsavedInserts().size());
        System.out.println("  Updates pending : " + service.getUnsavedUpdates().size());
        String confirm = Validate.validate(getString("  Save all changes to database? (y/n)"));
        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("  Cancelled."); return; }

        boolean ok = service.save();
        System.out.println(ok ? GREEN + "  ✔ All changes saved successfully." + RESET
                : RED + "  ✘ Save failed. Check database connection." + RESET);
    }

    private void viewUnsavedChanges() {
        List<Product> ins = service.getUnsavedInserts();
        List<Product> upd = service.getUnsavedUpdates();

        System.out.println(YELLOW + "\n── Unsaved Changes ─────────────────────────" + RESET);

        System.out.println("  Pending Inserts (" + ins.size() + "):");
        if (!ins.isEmpty()) TableHelper.displayTable(ins);

        System.out.println("  Pending Updates (" + upd.size() + "):");
        if (!upd.isEmpty()) TableHelper.displayTable(upd);


        if (!service.hasUnsavedChanges()) System.out.println(GREEN + "  All changes are saved." + RESET);
    }

    private void paginationMenu() {
        boolean inPagination = true;
        while (inPagination) {
            TableHelper.displayTable(service.getPageProducts(),service.getCurrentPage(),service.getTotalPages());
            System.out.println(CYAN + "\n  [F]. First  [N]. Next  [P]. Previous  [L]. Last  [G]. Goto  [B]. Back" + RESET);
            String cmd = getString("==> Navigation : ").toUpperCase();
            switch (cmd) {
                case "F" -> service.goToFirstPage();
                case "N" -> service.goToNextPage();
                case "P" -> service.goToPreviousPage();
                case "L" -> service.goToLastPage();
                case "G" -> {
                    int page = (int) getNumber("  Go to page : ");
                    service.goToPage(page);
                }
                case "B" -> inPagination = false;
                default  -> System.out.println(RED + "  Invalid option." + RESET);
            }
        }
    }

    private boolean exitApp() {
        if (service.hasUnsavedChanges()) {
            System.out.println(YELLOW + "  ⚠ You have unsaved changes!" + RESET);
            String choice = Validate
                    .validate(getString("  Save before exit? (y/n/cancel)").toLowerCase());
            if (choice.equals("cancel") || choice.equals("c")) return false;
            if (choice.equals("y")) service.save();
        }
        System.out.println(CYAN + "\n  Goodbye! Thank you for using Stock Management System." + RESET);
        return true;
    }

}
