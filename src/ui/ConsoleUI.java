package ui;

import domain.Product;
import service.StockService;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleUI {
    private final StockService service = new StockService();
    private final Scanner sc = new Scanner(System.in);

    public void start(){
        service.loadFromDatabase();
        while(true) {
            String choice = ReadOption("==> Choose an option(w, r, e): ");
            switch (choice) {
                case "w" -> handleWrite();
                case "r" -> handleRead();
                case "e" -> {
                    System.out.println("Exit ...");
                    return;
                }
                default -> System.out.println("Unknown option");
            }
        }
    }

    public String ReadOption(String messsage){
        while(true) {
            System.out.print(messsage);
            String input = sc.nextLine().toLowerCase();
            if (input.trim().isEmpty()) {
                System.out.println("Invalid input");
                continue;
            }
            if (!input.matches("[a-zA-Z]{1}")){
                System.out.println("Please input a character");
                continue;
            }
            return input;
        }
    }
    public void handleWrite(){
        int previewId = service.previewNextProductId();
        System.out.println("ID: " + previewId);
        String product = readProduct("Input product name: ");
        double price = readDouble("Input product price: ");
        int quantity = readInt("Input product quantity: ");
        Product Product = new Product(product, price, quantity, LocalDate.now());
        service.addProduct(Product);
        System.out.println("Enter to continue");
        sc.nextLine();
    }

    public void handleRead(){
            int ID = readInt("Please input id to get record : ");
            Product product = service.getProductById(ID);
            if (product == null) {
                System.out.println("Product not found");
                return;
            }
            CellStyle numberStyle = new CellStyle(CellStyle.HorizontalAlign.center);

            Table t = new Table(5, BorderStyle.UNICODE_ROUND_BOX,
                    ShownBorders.ALL);
            t.setColumnWidth(0, 20, 30);
            t.setColumnWidth(1, 20, 30);
            t.setColumnWidth(2, 20, 30);
            t.setColumnWidth(3, 20, 30);
            t.setColumnWidth(4, 20, 30);

            t.addCell("ID", numberStyle);
            t.addCell("Name", numberStyle);
            t.addCell("Unit Price", numberStyle);
            t.addCell("Qty", numberStyle);
            t.addCell("Import Date", numberStyle);

            t.addCell(String.valueOf(product.getProductId()), numberStyle);
            t.addCell(product.getProductName(), numberStyle);
            t.addCell(String.valueOf(product.getUnitPrice()), numberStyle);
            t.addCell(String.valueOf(product.getQuantity()), numberStyle);
            t.addCell(String.valueOf(product.getImportedDate()), numberStyle);
            System.out.println(t.render());

    }
    public String readProduct(String messsage) {
        while (true) {
            System.out.print(messsage);
            String input = sc.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Invalid input");
                continue;
            }
            if (!input.matches("^[a-zA-Z].*$")) {
                System.out.println("Please input a character");
                continue;
            }
            return input;
        }
    }
    public int readInt(String messsage) {
        while (true) {
            System.out.print(messsage);
            String input = sc.nextLine().trim();
            if (input == null) {
                System.out.println("Invalid input");
                continue;
            }
            if (!input.matches("^[0-9]+$")) {
                System.out.println("Please input a number");
                continue;
            }
            return Integer.parseInt(input);
        }
    }
    public double readDouble(String messsage) {
        while (true) {
            System.out.print(messsage);
            String input = sc.nextLine().trim();
            if (input.trim().isEmpty()) {
                System.out.println("Invalid input");
                continue;
            }
            if (!input.matches("^\\d*\\.?\\d*$")) {
                System.out.println("Please input a number");
                continue;
            }
            return Double.parseDouble(input);
        }
    }

}
