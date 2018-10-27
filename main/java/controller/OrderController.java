package controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Order;
import service.OrderItemService;
import service.OrderService;
import service.UserService;
import util.Page;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    UserService userService;

    //后台订单页面
    @RequestMapping("admin_order_list")
    public String listOrder(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> os = orderService.list();
        int total = (int) new PageInfo<>(os).getTotal();
        page.setTotal(total);
        model.addAttribute("os", os);
        model.addAttribute("page", page);
        return "admin/listOrder";
    }

    //后台订单界面的发货按钮
    @RequestMapping("admin_order_delivery")
    public String delivery(int id) {
        Order order = orderService.get(id);
        //订单状态更新为待确认收货
        order.setStatus(OrderService.waitConfirm);
        order.setDeliveryDate(new Date());
        orderService.update(order);
        return "redirect:admin_order_list";
    }
}
