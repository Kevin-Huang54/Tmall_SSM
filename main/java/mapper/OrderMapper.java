package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Order;

import java.util.List;

public interface OrderMapper {
    void add(Order order);
    void delete(int id);
    void update(Order order);
    Order get(int id);
    List<Order> list();
    List<Order> listByUid(int uid);
    List<Order> listByStatus(@Param("uid")int uid, @Param("status")String status);
}
