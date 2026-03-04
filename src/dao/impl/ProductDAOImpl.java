package dao.impl;

import config.DatabaseConfig;
import dao.ProductDAO;
import domain.Product;
import mapper.ProductMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.println;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void insert(Product product, Connection conn) {

    }

    @Override
    public void update(Product product,Connection conn) {

    }

    @Override
    public void delete(int idToDelete) {
        // validate the product id
        if (idToDelete <= 0) {
            println("Invalid ID: " + idToDelete);
            return;
        }
        // sql statement for delete the product
        String sql =
                """ 
                DELETE FROM products WHERE product_id=?
                """;
        // get connection and create prepare statement for delete
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idToDelete);
            int affectedRows = ps.executeUpdate();
            if(affectedRows>0)
                println("Successfully deleted product with ID: " + idToDelete);
            else
                println("No product found with ID: " + idToDelete);

        } catch (SQLException e) {
            System.err.println("Delete error: " + e.getMessage());
        }

    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_deleted=FALSE ORDER BY product_id";
        try (Statement st = DatabaseConfig.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(ProductMapper.mapProduct(rs));
        } catch (SQLException e) {
            System.err.println("FindAll error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean saveToDatabase(List<Product> toInsert, List<Product> toUpdate) {
        try(Connection conn = DatabaseConfig.getConnection()) {
            conn.setAutoCommit(false);
            try {
                for (Product p : toInsert) insert(p,conn);
                for (Product p : toUpdate) update(p,conn);
                conn.commit();
                conn.setAutoCommit(true);
                return true;
            } catch (Exception e) {
                conn.rollback();
                conn.setAutoCommit(true);
                System.err.println("Save transaction error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
        return false;
    }
}
