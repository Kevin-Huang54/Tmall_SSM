package controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pojo.Category;
import service.CategoryService;
import util.ImageUtil;
import util.Page;
import util.UploadedImageFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String listCategory(Model model, Page page) {
        //pagehelper会自动在数据库查询时加入limit
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs = categoryService.list();
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String addCategory(Category category, HttpSession session, UploadedImageFile image) throws IOException {
        //分类的name已自动注入category对象，直接就可以add
        categoryService.add(category);
        //getRealPath获取项目运行目录，也就是sever.xml中的docBase
        File imgPath = new File(session.getServletContext().getRealPath("img/category"));
        File imgFile = new File(imgPath, category.getId() + ".jpg");
        if (!imgFile.getParentFile().exists()) {
            //如不存在父文件夹，则创建
            imgFile.getParentFile().mkdirs();
        }
        //复制文件到指定位置
        image.getImage().transferTo(imgFile);
        //转换格式
        BufferedImage bufferedImage = ImageUtil.change2jpg(imgFile);
        //重新写入
        ImageIO.write(bufferedImage, "jpg", imgFile);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String deleteCategory(int cid, HttpSession session) {
        categoryService.delete(cid);
        File imgPath = new File(session.getServletContext().getRealPath("img/category"));
        File imgFile = new File(imgPath, cid + ".jpg");
        imgFile.delete();
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String editCategory(Model model, int cid) {
        Category category = categoryService.get(cid);
        model.addAttribute("c", category);
        //跳转到编辑界面
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String updateCategory(Category category, HttpSession session, UploadedImageFile image) throws IOException {
        categoryService.update(category);
        File imgPath = new File(session.getServletContext().getRealPath("img/category"));
        File imgFile = new File(imgPath, category.getId() + ".jpg");

        MultipartFile multipartFile= image.getImage();
        //判断用户是否上传了文件
        if (null != multipartFile && !multipartFile.isEmpty()) {
            //复制文件到指定位置
            multipartFile.transferTo(imgFile);
            //转换格式
            BufferedImage bufferedImage = ImageUtil.change2jpg(imgFile);
            //重新写入
            ImageIO.write(bufferedImage, "jpg", imgFile);
        }
        return "redirect:/admin_category_list";

    }
}
