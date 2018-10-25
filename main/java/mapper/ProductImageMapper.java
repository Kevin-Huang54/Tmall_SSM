package mapper;

import pojo.ProductImage;

import java.util.List;

public interface ProductImageMapper {
    void add(ProductImage productImage);
    void delete(int id);
    void update(ProductImage productImage);
    ProductImage get(int id);
    List<ProductImage> list(int pid, String type);
}
