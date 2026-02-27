package dao;

import domain.Product;

import java.util.List;

public interface ProductDAO {

    // CRUD
    boolean insert (Product product);
    boolean update(Product product);
    boolean delete(int id);
    Product findById(int id);
    List<Product> findAll();

    // search by productName
    List<Product> searchByName (String name);

    // Pagination
    List<Product> findWithPagination(int offset, int limit);

    // Save to DB
    boolean saveToDatabase (List<Product> toInsert, List<Product> toUpdate, List<Integer> toDelete);

}
