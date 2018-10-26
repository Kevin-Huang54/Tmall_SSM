package service;

import pojo.OrderItem;

import java.util.List;

public interface OrderItemService {
    void add(OrderItem orderItem);
    void delete(int id);
    void update(OrderItem orderItem);
    void updateCart(OrderItem orderItem);
    OrderItem get(int id);
    OrderItem getByUserAndProduct(int uid, int pid);
    List<OrderItem> list(int oid);
//    List<OrderItem> listByPid(int pid);
//    List<OrderItem> listByUid(int uid);
    List<OrderItem> listCartByUid(int uid);

    Integer getSaleCount(int pid);
}
