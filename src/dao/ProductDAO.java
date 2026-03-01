package dao;

import domain.Product;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Optional;

public interface ProductDAO {

    // CRUD
    boolean insert(Product product);
    boolean update(Product product);
    boolean delete(int id);
    Optional<Product> findById(int id);
    List<Product> findAll();

    // search by productName
    Optional<List<Product>> searchByName (String name);

    // Pagination
    List<Product> findWithPagination(int offset, int limit);
    int getTotalCount();

    // Save to DB
    boolean saveToDatabase (List<Product> toInsert, List<Product> toUpdate, List<Integer> toDelete);

}
