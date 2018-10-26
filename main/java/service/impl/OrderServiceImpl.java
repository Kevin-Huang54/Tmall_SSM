package service.impl;

import mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.Order;
import pojo.OrderItem;
import pojo.User;
import service.OrderItemService;
import service.OrderService;
import service.UserService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    OrderItemService orderItemService;
    
    @Override
    public void add(Order order) {
        orderMapper.add(order);
    }

    //用于前台提交订单页面，新建订单，同时将订单项与其oid关联
    @Override
    //事务管理，新建订单与订单项更新必须同时完成
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        add(order);
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        //返回订单总额
        return total;
    }

    @Override
    public void delete(int id) {
        orderMapper.delete(id);
    }

    //用于更新订单状态
    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }

    @Override
    public Order get(int id) {
        Order order = orderMapper.get(id);
        User user = userService.get(order.getUid());
//            根据uid设置user参数
        order.setUser(user);
//            填充该订单的订单项
        List<OrderItem> orderItems = orderItemService.list(order.getId());
//            计算订单商品总金额
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        order.setTotal(total);
        order.setTotalNumber(orderItems.size());
        order.setOrderItems(orderItems);
        return order;
    }

    @Override
    public List<Order> list() {
        List<Order> os = orderMapper.list();
        fill(os);
        return os;
    }

    @Override
    public List<Order> listByUid(int uid) {
        List<Order> os = orderMapper.listByUid(uid);
        fill(os);
        return os;
    }

//    public List<Order> listByStatus(int uid, String status) {
//        List<Order> os = orderMapper.listByStatus(uid, status);
//        fill(os);
//        return os;
//    }

    //为order对象填充orderItems属性
    private void fill(List<Order> os) {
        for (Order order : os) {
            User user = userService.get(order.getUid());
//            根据uid设置user参数
            order.setUser(user);
//            填充该订单的订单项
            List<OrderItem> orderItems = orderItemService.list(order.getId());
//            计算订单商品总金额
            float total = 0;
            for (OrderItem orderItem : orderItems) {
                total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            }
            order.setTotal(total);
            order.setTotalNumber(orderItems.size());
            order.setOrderItems(orderItems);
        }
    }
}
