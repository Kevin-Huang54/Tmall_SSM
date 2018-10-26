package service.impl;

import mapper.ProductImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.ProductImage;
import service.ProductImageService;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageMapper productImageMapper;

    @Override
    //数据库中增加项目，需要在controller中配合上传文件
    public void add(ProductImage productImage) {
        productImageMapper.add(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.delete(id);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.get(id);
    }

    @Override
    public List<ProductImage> list(int pid, String type) {
        return productImageMapper.list(pid, type);
    }
}
