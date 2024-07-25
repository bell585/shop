package com.example.demo.util;

import com.example.demo.execption.MyException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static final String detailsImages = "/D:/testjava/demo/src/main/resources/static/images/detailsImages/";
    public static final String rotateImages = "/D:/testjava/demo/src/main/resources/static/images/rotateImages/";
    public static final String cspImages = "/D:/testjava/demo/src/main/resources/static/images/cspImages/";
    public static final String detailsImagesPath = "/Shopping/static/images/detailsImages/";
    public static final String rotateImagesPath = "/Shopping/static/images/rotateImages/";
    public static final String cspImagesPath = "/Shopping/static/images/cspImages/";

    public static String setImage(MultipartFile file, String ImageType) {
        String fileName = file.getOriginalFilename();
        String baseName = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        if (extension != null && (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("gif"))) {
            // 进行其他操作，例如保存文件等
            String newFileName = baseName + "_" + System.currentTimeMillis() + "." + extension;
            File destFile = new File(ImageType + newFileName);
            try {
                file.transferTo(destFile);
            } catch (IOException e) {
                throw new MyException("文件上传异常");
            }
            return newFileName;
        } else {
            throw new MyException("文件非图片");
        }


    }

    public static String ImagesFullPath(String imageNames) { //拼接图片完整请求路径
        String[] imageNameArray = imageNames.split(",");
        String imagePath = "/Shopping/static/images/detailsImages/"; // 图片存放的路径
        StringBuilder description = new StringBuilder();
        for (String imageName : imageNameArray) {
            if (!imageName.isEmpty()) {
                String fullPath = imagePath + imageName;
                description.append(fullPath).append(",");
            }
        }
        return description.toString();
    }
    public static void deleteImages(String imageName,String path){
        String imagePath = path+imageName;
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            if (!imageFile.delete()) {
                System.out.println(imagePath+"图片删除失败");
            }
        }
    }


}
