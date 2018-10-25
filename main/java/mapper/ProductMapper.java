package mapper;

import pojo.Product;

import java.util.List;

public interface ProductMapper {
    void add(Product product);
    void delete(int id);
    void update(Product product);
    Product get(int id);
    List<Product> list(int cid);
    List<Product> search(String keyword);
}
