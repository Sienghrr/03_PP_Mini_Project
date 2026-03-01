package ui;

import dao.ProductDAO;
import dao.impl.ProductDAOImpl;
import service.StockService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final StockService service = new StockService();
    private final Scanner sc = new Scanner(System.in);
    private final ProductDAO productDAO = new ProductDAOImpl();
    public void start(){
        service.loadFromDatabase();
        List<Integer> productsID = service.getProductsID();
        while (true){
            System.out.print("=> Choose an Option(): ");
            String inputOption = sc.nextLine();
            if(!inputOption.matches("[a-zA-z]+")){
                System.out.println("Invalid Input Option");
            }else {
                switch (inputOption.toLowerCase()){
                    case "d" -> {
                        while (true){
                            System.out.print("Please input id to delete record : ");
                            String inputIdToDelete = sc.nextLine();
                            if (!inputIdToDelete.matches("\\d+")){
                                System.out.println("ID must be the number");
                            }else if(productsID.contains(Integer.parseInt(inputIdToDelete))){
                                productDAO.delete(Integer.parseInt(inputIdToDelete));
                                break;
                            }else {
                                System.out.println("ID doesn't exist");
                            }
                        }


                    }
                    case "u" -> System.out.println("Update function");
                    default -> System.out.println("Invalid Option");
                }
                break;
            }
        }


        System.out.println("✔ Connected to database and loaded products.");
    }
}
