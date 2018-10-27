package Comparator;

import pojo.Product;

import java.util.Comparator;

//用于综合排序的比较器，供控制层的sort方法调用
public class ProductAllComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() * o2.getSaleCount() - o1.getReviewCount() * o1.getSaleCount();
    }
}
