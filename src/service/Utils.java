package service;

import domain.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.Scanner;

public class Utils {
    private static final Scanner sc = new Scanner(System.in);
//    private
    public static String askToConfirm(int id){
        System.out.print("Are you sure to delete product id : " + id + " ? ");
        return sc.nextLine();
    }
    public static void oneRowTable(Product product){
        CellStyle numberStyle = new CellStyle(CellStyle.HorizontalAlign.center);

        Table t = new Table(5, BorderStyle.UNICODE_ROUND_BOX,
                ShownBorders.ALL);
        t.setColumnWidth(0, 20, 30);
        t.setColumnWidth(1, 20, 30);
        t.setColumnWidth(2, 20, 30);
        t.setColumnWidth(3, 20, 30);
        t.setColumnWidth(4, 20, 30);

        t.addCell("ID",numberStyle);
        t.addCell("Name", numberStyle);
        t.addCell("Unit Price", numberStyle);
        t.addCell("Qty", numberStyle);
        t.addCell("Import Date", numberStyle);

        t.addCell(String.valueOf(product.getProductId()),numberStyle);
        t.addCell(product.getProductName(), numberStyle);
        t.addCell(String.valueOf(product.getUnitPrice()), numberStyle);
        t.addCell(String.valueOf(product.getQuantity()),numberStyle);
        t.addCell(String.valueOf(product.getImportedDate()), numberStyle);
        System.out.println(t.render());
    }
}
