package mapper;

import domain.Product;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProductMapper {
    public static Product mapProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setUnitPrice(rs.getDouble("unit_price"));
        product.setQuantity(rs.getInt("quantity"));
        Date d = rs.getDate("imported_date");
        product.setImportedDate(d != null ? d.toLocalDate() : LocalDate.now());
        product.setDeleted(rs.getBoolean("is_deleted"));
        return product;
    }
}
