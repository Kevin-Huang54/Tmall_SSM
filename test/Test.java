import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pojo.Category;
import pojo.OrderItem;
import pojo.User;
import service.CategoryService;
import service.OrderItemService;
import service.PropertyValueService;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:resources/applicationContext.xml"})

public class Test {
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    PropertyValueService propertyValueService;

    @org.junit.Test
    public void myTest() {
        Category category = new Category();
        category.setName("test");
        categoryService.add(category);
        System.out.println(category.getId());
    }
}
