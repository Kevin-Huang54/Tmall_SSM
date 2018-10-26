package service.impl;

import mapper.OrderItemMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.OrderItem;
import pojo.Product;
import service.OrderItemService;
import service.OrderService;
import service.ProductService;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    ProductService productService;
    @Autowired
    static OrderItemMapper orderItemMapperstatic;

    @Override
    //立即购买，加入购物车
    public void add(OrderItem orderItem) {
        orderItemMapper.add(orderItem);
    }

    @Override
    //删除购物车内的产品
    public void delete(int id) {
        orderItemMapper.delete(id);
    }

    @Override
    //更新订单状态，代发货、待付款、待评价等
    public void update(OrderItem orderItem) {
        orderItemMapper.update(orderItem);
    }

    @Override
    //更新购物车的产品数量
    public void updateCart(OrderItem orderItem) {
        orderItemMapper.updateCart(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        OrderItem orderItem = orderItemMapper.get(id);
        Product product = productService.get(orderItem.getPid());
        orderItem.setProduct(product);
        return orderItem;
    }

    @Override
    //用于判断该用户购物车内是否存在该产品
    public OrderItem getByUserAndProduct(int uid, int pid) {
        return orderItemMapper.getByUserAndProduct(uid, pid);
    }

    @Override
    //用于fill方法填充order，controller中未使用
    public List<OrderItem> list(int oid) {
        List<OrderItem> orderItems = orderItemMapper.list(oid);
        for (OrderItem orderItem : orderItems) {
            Product product = productService.get(orderItem.getPid());
            orderItem.setProduct(product);
        }
        return orderItems;
    }

//    未使用
//    @Override
//    public List<OrderItem> listByPid(int pid) {
//        List<OrderItem> orderItems = orderItemMapper.list(pid);
//        for (OrderItem orderItem : orderItems) {
//            Product product = productService.get(pid);
//            orderItem.setProduct(product);
//        }
//        return orderItems;
//    }

//    未使用
//    @Override
//    public List<OrderItem> listByUid(int uid) {
//        List<OrderItem> orderItems = orderItemMapper.listByUid(uid);
//        for (OrderItem orderItem : orderItems) {
//            Product product = productService.get(orderItem.getPid());
//            orderItem.setProduct(product);
//        }
//        return orderItems;
//    }

    @Override
    //查询该用户的订单项
    public List<OrderItem> listCartByUid(int uid) {
        List<OrderItem> orderItems = orderItemMapper.listCartByUid(uid);
        for (OrderItem orderItem : orderItems) {
            Product product = productService.get(orderItem.getPid());
            orderItem.setProduct(product);
        }
        return orderItems;
    }

    @Override
    //product的service中用于设置产品销量
    public Integer getSaleCount(int pid) {
        Integer saleCountGet = orderItemMapper.getSaleCount(pid);
        Integer saleCount = saleCountGet == null ? 0 : saleCountGet;
        return saleCount;
    }
}
