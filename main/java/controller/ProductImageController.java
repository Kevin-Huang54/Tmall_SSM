package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Category;
import pojo.Product;
import pojo.ProductImage;
import service.CategoryService;
import service.ProductService;
import service.ProductImageService;
import util.ImageUtil;
import util.UploadedImageFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    //列出产品图片，展示图片和详情图片
    @RequestMapping("admin_productImage_list")
    public String listProductImage(Model model, int pid) {
        List<ProductImage> pisSingle = productImageService.list(pid, ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid, ProductImageService.type_detail);
        Product product = productService.get(pid);
        Category category = categoryService.get(product.getCid());
        model.addAttribute("pisSingle", pisSingle);
        model.addAttribute("pisDetail", pisDetail);
        model.addAttribute("product", product);
        model.addAttribute("category", category);
        return "admin/listProductImage";
    }

    //上传图片按钮
    @RequestMapping("admin_productImage_add")
    public String addProductImage(ProductImage pi, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        productImageService.add(pi);
        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small = null;
        String imageFolder_middle = null;
        //如果类型是展示图片，定义原图、小图、中图文件夹
        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder= session.getServletContext().getRealPath("img/product/type_single");
            imageFolder_small= session.getServletContext().getRealPath("img/product/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/product/productSingle_middle");
        }
        //如果类型是详情图片，定义原图文件夹
        else{
            imageFolder= session.getServletContext().getRealPath("img/product/type_detail");
        }

        //按照各自对应文件夹拷贝图片
        File f = new File(imageFolder, fileName);
        f.getParentFile().mkdirs();
        try {
            uploadedImageFile.getImage().transferTo(f);
            BufferedImage img = ImageUtil.change2jpg(f);
            ImageIO.write(img, "jpg", f);

            //如果是首页图片，额外增加小图和中图
            if(ProductImageService.type_single.equals(pi.getType())) {
                File f_small = new File(imageFolder_small, fileName);
                File f_middle = new File(imageFolder_middle, fileName);
                //改变大小后直接写入
                ImageUtil.resizeImage(f, 56, 56, f_small);
                ImageUtil.resizeImage(f, 217, 190, f_middle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin_productImage_list?pid=" + pi.getPid();
    }

    //删除图片
    @RequestMapping("admin_productImage_delete")
    public String deleteProductImage(int id, HttpSession session) {
        ProductImage productImage = productImageService.get(id);
        int pid = productImage.getPid();
        productImageService.delete(id);
        File imgPath = new File(session.getServletContext().getRealPath("img/product/" + productImage.getType()));
        File imgFile = new File(imgPath, productImage.getId() + ".jpg");
        imgFile.delete();
        return "redirect:/admin_productImage_list?pid=" + pid;
    }
}
