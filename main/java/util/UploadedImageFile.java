package util;

import org.springframework.web.multipart.MultipartFile;

public class UploadedImageFile {
    //封装spring的MultipartFile对象
    MultipartFile image;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
