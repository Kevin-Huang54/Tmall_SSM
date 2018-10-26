package service.impl;

import mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Category;
import pojo.Product;
import pojo.ProductImage;
import service.OrderItemService;
import service.ProductImageService;
import service.ProductService;
import service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;
    
    @Override
    public void add(Product product) {
        productMapper.add(product);
    }

    @Override
    public void delete(int id) {
        productMapper.delete(id);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

    @Override
    public Product get(int id) {
        Product product = productMapper.get(id);
        setFirstProductImage(product);
        return product;
    }

    @Override
    public List<Product> list(int cid) {
        List<Product> products = productMapper.list(cid);
        for (Product product : products) {
            //为所有产品设置显示图片
            setFirstProductImage(product);
        }
        return products;
    }

    @Override
    //填充category对象的‘产品列表’属性
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fill(List<Category> categories) {
        for (Category category : categories) {
            fill(category);
        }
    }

    @Override
    public void fillByRow(List<Category> categories) {
        //填充category的productsByRow属性，用于首页左侧的导航栏
        //定义每行显示产品数为4，将products分为每4个product一个list，再组成一个list
        int eachRow = 4;
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += eachRow) {
                int end = i + eachRow;
                end = end > products.size() ? products.size() : end;
                List<Product> productEachRow = products.subList(i, end);
                productsByRow.add(productEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            //将第一张上传的图片设置为展示图片
            p.setFirstProductImage(pis.get(0));
        }
    }

    @Override
    //设置销量和评价数量，用于分类页和产品页展示
    public void setSaleAndReviewNumber(Product p) {
        int saleNumber = orderItemService.getSaleCount(p.getId());
        int reviewNumber = reviewService.getCount(p.getId());
        p.setSaleCount(saleNumber);
        p.setReviewCount(reviewNumber);
    }

    @Override
    public void setSaleAndReviewNumber(List<Product> ps) {
        for (Product p : ps) {
            setSaleAndReviewNumber(p);
        }
    }

    @Override
    //产品搜索功能
    public List<Product> search(String keyword) {
        List<Product> products = productMapper.search(keyword);
        for (Product product : products) {
            setFirstProductImage(product);
        }
        return products;
    }
}
