package service;

import pojo.ProductImage;

import java.util.List;

public interface ProductImageService {
    String type_single = "type_single";
    String type_detail = "type_detail";

    void add(ProductImage productImage);
    void delete(int id);
    ProductImage get(int id);
    List<ProductImage> list(int pid, String type);
}