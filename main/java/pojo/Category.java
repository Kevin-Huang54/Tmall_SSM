package pojo;

import java.util.List;

public class Category {
    private int id;
    private String name;
    //前台新增，用于分类下的产品显示
    List<Product> products;
    //前台左侧导航栏专用
    List<List<Product>> productsByRow;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {

        this.productsByRow = productsByRow;
    }
}