package com.zyc.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2018/1/21 0021.
 */
@Service
public class FileUploadService {
    private Map<String,String> types=new HashMap<String,String>();
    public FileUploadService(){
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");
    }
    public String fileUpload(MultipartHttpServletRequest request, CommonsMultipartResolver commonsMultipartResolver){
        commonsMultipartResolver.setDefaultEncoding("utf-8");//上传头像的文件名可以是中文
        commonsMultipartResolver.setResolveLazily(true);//设置延迟加载
        commonsMultipartResolver.setMaxInMemorySize(1024*1024*4);//设置缓冲
        commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024);//设置每个文件大小
        commonsMultipartResolver.setMaxUploadSize(1024*1024*2);//设置最多能上传文件的大小


        MultipartFile multipartFile=request.getFile("imgFile");//取得上传图片的文件域的name值

        String fileType=types.get(multipartFile.getContentType());//取出头像的文件类型
        //String fileName=file.getOriginalFilename();

        UUID uuid= UUID.randomUUID();
        //组合新上传的文件所在路径
        String path=FileUploadService.class.getClassLoader().getResource("").getPath();
        String dir=request.getParameter("dir");
        String filePath=path+"static/editor/upload/"+dir+"/"+uuid.toString()+fileType;

        File file=new File(filePath);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //提供json格式，两个键 error：0，url：文件路径
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("error",0);
        jsonObject.put("url","/static/editor/upload/"+dir+"/"+uuid.toString()+fileType);
        System.out.println(jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
