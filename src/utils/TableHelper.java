package utils;

import domain.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.List;

import static utils.Utils.*;

public class TableHelper {

    private static final CellStyle CENTER = new CellStyle(CellStyle.HorizontalAlign.center);
    private static final int COL_MIN = 20;
    private static final int COL_MAX = 64;


    public static void displayTable(List<Product> products, int currentPage, int totalPages) {
        Table table = buildTable(products);
        System.out.println(table.render());
        printFooter(currentPage, totalPages, products.size());
    }

    // Show all (page 1 of 1)
    public static void displayTable(List<Product> products) {
        displayTable(products, 1, 1);
    }

    // Single product
    public static void displayTable(Product product) {
        Table table = newTable();
        addHeader(table);
        addRow(table, product);
        System.out.println(table.render());
    }


    private static Table buildTable(List<Product> products) {
        Table table = newTable();
        addHeader(table);
        if (products.isEmpty()) {
            // span-style empty message by filling 5 blank cells
            for (int i = 0; i < 5; i++) {
                table.addCell(i == 2 ? RED + "No products found." + RESET : "", CENTER);
            }
        } else {
            for (Product p : products) addRow(table, p);
        }
        return table;
    }

    private static Table newTable() {
        Table table = new Table(5, BorderStyle.UNICODE_DOUBLE_BOX_WIDE, ShownBorders.ALL);
        table.setColumnWidth(0, COL_MIN, COL_MAX);
        table.setColumnWidth(1, COL_MIN, COL_MAX);
        table.setColumnWidth(2, COL_MIN, COL_MAX);
        table.setColumnWidth(3, COL_MIN, COL_MAX);
        table.setColumnWidth(4, COL_MIN, COL_MAX);
        return table;
    }

    private static void addHeader(Table table) {
        table.addCell(BOLD + BLUE + "ID"          + RESET, CENTER);
        table.addCell(BOLD + BLUE + "NAME"        + RESET, CENTER);
        table.addCell(BOLD + BLUE + "UNIT PRICE"  + RESET, CENTER);
        table.addCell(BOLD + BLUE + "QTY"         + RESET, CENTER);
        table.addCell(BOLD + BLUE + "IMPORT DATE" + RESET, CENTER);
    }

    private static void addRow(Table table, Product p) {
        String date = p.getImportedDate() != null ? p.getImportedDate().toString() : "N/A";
        table.addCell(GREEN  + p.getProductId()   + RESET, CENTER);
        table.addCell(CYAN   + p.getProductName() + RESET, CENTER);
        table.addCell(YELLOW + p.getUnitPrice()   + RESET, CENTER);
        table.addCell(CYAN   + p.getQuantity()    + RESET, CENTER);
        table.addCell(CYAN   + date               + RESET, CENTER);
    }

    private static void printFooter(int currentPage, int totalPages, int totalRecords) {
        System.out.printf(
                "  Page : " + BOLD + BLUE + "%d" + RESET +
                        " of "      + BOLD + BLUE + "%d" + RESET +
                        "     Total Record : " + BOLD + GREEN + "%d" + RESET + "%n",
                currentPage, totalPages, totalRecords
        );
    }
}