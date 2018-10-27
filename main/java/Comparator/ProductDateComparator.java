package Comparator;

import pojo.Product;

import java.util.Comparator;

//用于日期排序的比较器，供控制层的sort方法调用
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getCreateDate().compareTo(o1.getCreateDate());
    }
}
