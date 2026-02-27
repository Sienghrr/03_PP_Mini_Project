package ui;

import service.StockService;

import java.util.Scanner;

public class ConsoleUI {
    private final StockService service = new StockService();
    private final Scanner sc = new Scanner(System.in);

    public void start(){
        service.loadFromDatabase();
        System.out.println("✔ Connected to database and loaded products.");
    }
}
