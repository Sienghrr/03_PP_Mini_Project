package dao.impl;

import config.DatabaseConfig;
import dao.ProductDAO;
import domain.Product;
import mapper.ProductMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean insert(Product product) {
        return false;
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
            while (rs.next()) list.add(ProductMapper.mapProduct(rs));
        } catch (SQLException e) {
            System.err.println("FindAll error: " + e.getMessage());
        }
        return list;
    }

    @Override
        public List<Product> searchByName(String name, List<Product> Product) {

            String search = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection con = DriverManager.getConnection(search);
             PreparedStatement ps = con.prepareStatement(search)
            ){
                ps.setString(1, "%" + name + "%");

                ResultSet rs = ps.executeQuery();

                boolean found = false;

                System.out.println("--------------------------------------------------");
                System.out.printf("%-5s %-20s %-10s %-5s %-20s\n",
                        "product_id", "product_name", "unit_price", "quantity", "imported_date");
                System.out.println("--------------------------------------------------");

                while (rs.next()) {
                    found = true;

                    System.out.printf("%-5d %-15s %-10.2f %-5d %-12s\n",
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("unit_price"),
                            rs.getInt("qty"),
                            rs.getDate("import_date")
                    );
                }

                if (!found) {
                    System.out.println("No product found!");
                }
                System.out.println("--------------------------------------------------");
            } catch (SQLException e) {
                e.getMessage();
            }
        return Product;
    }

    @Override
    public List<Product> findWithPagination(int offset, int limit) {
        return List.of();
    }

    @Override
    public int getTotalCount() {
        return 0;
    }

    @Override
    public boolean saveToDatabase(List<Product> toInsert, List<Product> toUpdate, List<Integer> toDelete) {
        return false;
    }
}
