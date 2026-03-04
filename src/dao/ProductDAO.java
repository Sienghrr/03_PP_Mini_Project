package dao;

import domain.Product;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    // CRUD
    void insert(Product product, Connection connection);
    void update(Product product, Connection connection);
    void delete(int id);
    List<Product> findAll();
    // Save to DB
    boolean saveToDatabase (List<Product> toInsert, List<Product> toUpdate);

}
