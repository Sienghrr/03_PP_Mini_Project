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
        return false;
    }
}
