package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.OrderItem;

import java.util.List;

public interface OrderItemMapper {
    void add(OrderItem orderItem);
    void delete(int id);
    void update(OrderItem orderItem);
    void updateCart(OrderItem orderItem);
    OrderItem get(int id);
    OrderItem getByUserAndProduct(@Param("uid")int uid, @Param("pid")int pid);
    List<OrderItem> list(int oid);
    List<OrderItem> listByPid(int pid);
    List<OrderItem> listByUid(int uid);
    List<OrderItem> listCartByUid(int uid);
    Integer getSaleCount(int pid);
}
