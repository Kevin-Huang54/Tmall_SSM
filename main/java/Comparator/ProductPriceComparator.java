package Comparator;

import pojo.Product;

import java.util.Comparator;

//用于价格排序的比较器，供控制层的sort方法调用
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return (int) (o1.getPromotePrice() - o2.getPromotePrice());
    }
}
