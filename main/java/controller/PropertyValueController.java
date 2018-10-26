package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Category;
import pojo.Product;
import pojo.Property;
import pojo.PropertyValue;
import service.*;
import service.PropertyService;
import util.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
public class PropertyValueController {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    PropertyService propertyService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    //点击后台产品页的属性编辑按钮，跳转至此
    @RequestMapping("admin_propertyValue_edit")
    public String editPropertyValue(Model model, int pid, int cid) {
        Category category = categoryService.get(cid);
        Product product = productService.get(pid);
        //查询并遍历出该分类的所有属性
        List<Property> properties = propertyService.list(cid);
        for (Property property : properties) {
            int ptid = property.getId();
            //如果属性值不存在，就新增一个插入数据库，value值为空
            if (Objects.isNull(propertyValueService.get(ptid, pid))) {
                PropertyValue propertyValue = new PropertyValue();
                propertyValue.setPid(pid);
                propertyValue.setPtid(ptid);
                propertyValue.setValue("");
                propertyValueService.add(propertyValue);
            }
        }
        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        model.addAttribute("properties", properties);
        model.addAttribute("propertyValues", propertyValues);
        model.addAttribute("category", category);
        model.addAttribute("product", product);
        return "admin/editPropertyValue";
    }

    //属性值表单提交后的操作
    @RequestMapping("admin_propertyValue_update")
    public String updatePropertyValue(int cid, int pid, HttpServletRequest request) {
        List<Property> properties = propertyService.list(cid);
        for (Property property : properties) {
            int ptid = property.getId();
            //用request取出属性名称对应的值，并更新数据库
            String value = request.getParameter(property.getName());
            PropertyValue propertyValue = new PropertyValue();
            propertyValue.setPid(pid);
            propertyValue.setPtid(ptid);
            propertyValue.setValue(value);
            propertyValueService.update(propertyValue);
        }
        return "redirect:/admin_propertyValue_edit?pid=" + pid + "&cid=" + cid;
    }
}
