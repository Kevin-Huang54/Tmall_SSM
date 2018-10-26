package controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Category;
import pojo.Product;
import service.CategoryService;
import service.ProductService;
import util.Page;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String listProduct(Model model, Page page, int cid) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> pds = productService.list(cid);
        Category category = categoryService.get(cid);
        int total = (int) new PageInfo<>(pds).getTotal();
        page.setTotal(total);
        //用于page.jsp中超链的追加参数
        page.setParam("&cid=" + cid);
        model.addAttribute("pds", pds);
        model.addAttribute("page", page);
        model.addAttribute("c", category);
        return "admin/listProduct";
    }

    @RequestMapping("admin_product_add")
    public String addProduct(Product product) {
        product.setCreateDate(new Date());
        productService.add(product);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

    @RequestMapping("admin_product_delete")
    public String deleteProduct(int id) {
        int cid = productService.get(id).getCid();
        productService.delete(id);
        return "redirect:/admin_product_list?cid=" + cid;
    }

    @RequestMapping("admin_product_edit")
    public String editProduct(Model model, int id) {
        Product product = productService.get(id);
        Category category = categoryService.get(product.getCid());
        model.addAttribute("p", product);
        model.addAttribute("c", category);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String updateProduct(Product product) {
        productService.update(product);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }
}
