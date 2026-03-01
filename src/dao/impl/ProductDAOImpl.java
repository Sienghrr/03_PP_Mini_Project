package dao.impl;

import config.DatabaseConfig;
import dao.ProductDAO;
import domain.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private Product mapRow(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setProductName(rs.getString("product_name"));
        p.setUnitPrice(rs.getDouble("unit_price"));
        p.setQuantity(rs.getInt("quantity"));
        Date importDate = rs.getDate("imported_date");
        p.setImportedDate(importDate != null ? importDate.toLocalDate() : LocalDate.now());
        p.setDeleted(rs.getBoolean("is_deleted"));
        return p;
    }

    @Override
    public boolean insert(Product product) {
        String sql = "INSERT INTO products (product_name, unit_price, quantity, imported_date) VALUES (?, ?, ?, ?)";
        try(Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql))  {
            Date sqlDate = Date.valueOf(product.getImportedDate());
            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getUnitPrice());
            ps.setInt(3, product.getQuantity());
            ps.setDate(4, sqlDate);
            int rows = ps.executeUpdate();
            return rows>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Product findById(int id) {
        return null;
    }

    @Override
    public List<Product> findAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_deleted=FALSE ORDER BY product_id";
        try (Statement st = DatabaseConfig.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("FindAll error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<Product> searchByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> findWithPagination(int offset, int limit) {
        return List.of();
    }

    @Override
    public boolean saveToDatabase(List<Product> toInsert, List<Product> toUpdate, List<Integer> toDelete) {
        return false;
    }
}
