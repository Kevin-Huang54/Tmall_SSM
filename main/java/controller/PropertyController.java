package controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Category;
import pojo.Property;
import service.CategoryService;
import service.PropertyService;
import util.Page;
import java.util.List;

@Controller
@RequestMapping("")
public class PropertyController {
    @Autowired
    PropertyService propertyService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_property_list")
    public String listProperty(Model model, Page page, int cid) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> ps = propertyService.list(cid);
        Category category = categoryService.get(cid);
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + cid);
        model.addAttribute("ps", ps);
        model.addAttribute("page", page);
        model.addAttribute("c", category);
        return "admin/listProperty";
    }

    @RequestMapping("admin_property_add")
    public String addProperty(Property property) {
        propertyService.add(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    @RequestMapping("admin_property_delete")
    public String deleteProperty(int id) {
        int cid = propertyService.get(id).getCid();
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid=" + cid;
    }

    @RequestMapping("admin_property_edit")
    public String editProperty(Model model, int id) {
        Property property = propertyService.get(id);
        model.addAttribute("p", property);
        return "admin/editProperty";
    }

    @RequestMapping("admin_property_update")
    public String updateProperty(Property property) {
        propertyService.update(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }
}
