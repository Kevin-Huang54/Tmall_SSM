package controller;

import Comparator.*;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;
import pojo.*;
import service.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    //    传递首页所需分类和产品数据
    @RequestMapping("foreHome")
    public String home(Model model) {
        List<Category> categories = categoryService.list();
        //为分类设置products属性
        productService.fill(categories);
        //为分类设置productsByRow属性, 用于分类导航栏右侧的产品显示
        productService.fillByRow(categories);
        model.addAttribute("cs", categories);
        return "fore/home";
    }

//    用户注册页面需要的功能
    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        if (userService.isExist(name)) {
            model.addAttribute("msg", "用户名已存在，不能使用");
            model.addAttribute("user", null);
            return "fore/register";
        }
        userService.add(user);
        return "redirect:registerSuccessPage";
    }

//    登陆功能
    @RequestMapping("forelogin")
    public String login(String name, String password, Model model, HttpSession session) {
        if (!userService.isExist(name)) {
            model.addAttribute("msg", "用户名不存在");
            model.addAttribute("user", null);
            return "fore/login";
        }
        User user = userService.getByNameAndPw(name, password);
        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        }
        //登录成功, 返回登陆后的首页
        session.setAttribute("user", user);
        return "redirect:foreHome";
    }

//    退出登陆
    @RequestMapping("forelogout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:foreHome";
    }

    //点击产品, 跳出对应产品详情页面
    @RequestMapping("foreproduct")
    public String product(Model model, int pid) {
        Product product = productService.get(pid);
        //设置产品分类
        int cid = product.getCid();
        product.setCategory(categoryService.get(cid));
        //设置图片
        product.setProductSingleImages(productImageService.list(pid, ProductImageService.type_single));
        product.setProductDetailImages(productImageService.list(pid, ProductImageService.type_detail));
        //设置销量和评论总数
        productService.setSaleAndReviewNumber(product);
        //设置产品属性值
        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        //设置评论内容
        List<Review> reviews = reviewService.list(pid);

        model.addAttribute("p", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("pvs", propertyValues);
        return "fore/product";
    }

    //加入购物车和立即购买, 点击后判断是否登录
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (null != user) {
            return "success";
        }
        return "fail";

    }

    //点击加入购物车和立即购买后, 登录页面Ajax需要判断用户名密码是否正确
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(@RequestParam("name")String name, @RequestParam("password")String password, HttpSession session) {
        User user = userService.getByNameAndPw(name, password);
        if (null == user) {
            return "fail";
        }
        session.setAttribute("user", user);
        return "success";
    }

    //分类页面产品展示
    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model) {
        List<Product> products = productService.list(cid);
        productService.setSaleAndReviewNumber(products);
        //根据sort参数选择排序方法
        if (null != sort) {
            switch (sort) {
                case "review":
                    Collections.sort(products, new ProductReviewComparator());
                    break;
                case "date":
                    Collections.sort(products, new ProductDateComparator());
                    break;
                case "saleCount":
                    Collections.sort(products, new ProductSaleCountComparator());
                    break;
                case "price":
                    Collections.sort(products, new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(products, new ProductAllComparator());
                    break;
            }
        }
        Category category = categoryService.get(cid);
        category.setProducts(products);

        model.addAttribute("c", category);
        return "fore/category";
    }

    //产品搜索功能
    @RequestMapping("foresearch")
    public String search(String keyword, Model model) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        productService.setSaleAndReviewNumber(products);
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }

    //立即购买按键跳转至此, 设置订单项
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        OrderItem orderItem = new OrderItem();
        orderItem.setPid(pid);
        orderItem.setUid(user.getId());
        orderItem.setNumber(num);
        int oiid = 0;
        OrderItem orderItemGet = orderItemService.getByUserAndProduct(user.getId(), pid);
        if (null != orderItemGet) {
            //如果购物车存在同一产品，则在原数量上累加
            orderItemGet.setNumber(orderItemGet.getNumber() + orderItem.getNumber());
            orderItemService.updateCart(orderItemGet);
            oiid = orderItemGet.getId();
        } else {
            //如果之前没有加入购物车，则新增项
            orderItemService.add(orderItem);
            oiid = orderItem.getId();
        }

        return "redirect:forebuy?oiid="+oiid;
    }

    //加入购物车按键跳转至此
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, HttpSession session) {
        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        OrderItem orderItem = new OrderItem();
        orderItem.setPid(pid);
        orderItem.setUid(user.getId());
        orderItem.setNumber(num);
        OrderItem orderItemGet = orderItemService.getByUserAndProduct(user.getId(), pid);
        if (null != orderItemGet) {
            //如果购物车存在同一产品，则在原数量上累加
            orderItemGet.setNumber(orderItemGet.getNumber() + orderItem.getNumber());
            orderItemService.updateCart(orderItemGet);
        } else {
            //如果之前没有加入购物车，则新增项
            orderItemService.add(orderItem);
        }
        return "success";
    }

    //跳转到查看购物车页面
    @RequestMapping("forecart")
    public String cart(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        int uid = user.getId();
        List<OrderItem> orderItems = orderItemService.listCartByUid(uid);
        session.setAttribute("ois", orderItems);
        return "fore/cart";
    }

    //用于处理各种页面跳转来的请求，并传递参数到订单结算页面
    @RequestMapping("forebuy")
    public String buy(String[] oiid, Model model, HttpSession session) {
        float total = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (String stroiid : oiid) {
            int id  = Integer.parseInt(stroiid);
            OrderItem orderItem = orderItemService.get(id);
            orderItems.add(orderItem);
            //计算总价
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        //购物车数量需要用session
        session.setAttribute("ois", orderItems);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    //购物车界面修改产品数量
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(int pid, int number, HttpSession session) {
        Product product = productService.get(pid);
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItems = orderItemService.listCartByUid(user.getId());
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getPid() == pid) {
                orderItem.setNumber(number);
                orderItemService.updateCart(orderItem);
                break;
            }
        }
        return "success";
    }

    //购物车界面删除订单项
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(int oiid) {
        orderItemService.delete(oiid);
        return "success";
    }

    //点击提交订单, 创建订单, 跳转到付款页面
    @RequestMapping("forecreateOrder")
    public String createOrder(HttpSession session, Order order) {
        User user = (User) session.getAttribute("user");
        order.setUid(user.getId());
        order.setCreateDate(new Date());
        //订单号为当前日期加上四位随机数
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setStatus(OrderService.waitPay);
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");

        float total = orderService.add(order, orderItems);
        session.setAttribute("oid", order.getId());
        session.setAttribute("total", total);
        return "fore/alipay";
    }

    //付款页面点击付款后, 更新订单状态
    @RequestMapping("forepayed")
    public String payed(HttpSession session, Model model) {
        int oid = (int) session.getAttribute("oid");
        Order order = orderService.get(oid);
        //更新订单状态为待发货
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        model.addAttribute("o", order);

        return "fore/payed";
    }

    //我的订单页面
    @RequestMapping("forebought")
    public String bought(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.listByUid(user.getId());
        model.addAttribute("os", orders);
        return "fore/bought";
    }

    //从订单页点击付款，跳转到付款页面
    @RequestMapping("forealipay")
    public String alipay(int oid, float total, HttpSession session) {
        session.setAttribute("oid", oid);
        session.setAttribute("total", total);
        return "fore/alipay";
    }

    //从订单页点击确认收货，跳转到确认付款页面
    @RequestMapping("foreconfirmPay")
    public String confirmPay(int oid, Model model) {
        Order order = orderService.get(oid);
        model.addAttribute("o", order);
        return "fore/confirmPay";
    }

    //在确认收货页面，点击确认收货，完成该订单
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(int oid, Model model) {
        Order order = orderService.get(oid);
        order.setConfirmDate(new Date());
        order.setStatus(OrderService.waitReview);
        orderService.update(order);
        return "fore/orderConfirmed";
    }

    //我的订单页面，点击删除订单
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.delete);
        orderService.update(order);
        return "success";
    }

    //跳转到评价订单页面
    @RequestMapping("forereview")
    public String review(int oid, Model model) {
        Order order = orderService.get(oid);
        OrderItem firstOrderItem = order.getOrderItems().get(0);
        Product product = firstOrderItem.getProduct();
        List<Review> reviews = reviewService.list(product.getId());

        model.addAttribute("p", product);
        model.addAttribute("o", order);
        model.addAttribute("reviews", reviews);
        return "fore/review";
    }

    //提交评价操作, 提交后跳转到产品评价页面, 并只显示刚提交的评价
    @RequestMapping("foredoreview")
    public String doReview(Model model, HttpSession session, int pid, int oid, String content) {
        User user = (User) session.getAttribute("user");
        content = HtmlUtils.htmlEscape(content);
        //插入review数据库条目
        Review review = new Review();
        review.setContent(content);
        review.setCreateDate(new Date());
        review.setPid(pid);
        review.setUid(user.getId());
        reviewService.add(review);
        //更新订单状态为‘已完成’
        Order order = orderService.get(oid);
        order.setStatus(OrderService.finish);
        orderService.update(order);
        //showonly=true表示提交后只显示自己的评价
        return "redirect:forereview?oid="+oid+"&showonly=true";
    }
}
