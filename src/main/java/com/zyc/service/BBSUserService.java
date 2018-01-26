package com.zyc.service;

import com.zyc.dao.IBBSUserDao;
import com.zyc.po.Bbsuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
@Service
@Transactional
public class BBSUserService {
    private Map<String,String> types=new HashMap<String, String>();
    public BBSUserService(){
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/x-ms-bmp", ".bmp");
        types.put("image/png", ".png");

    }
    @Autowired
    private IBBSUserDao dao;
    public Bbsuser login(Bbsuser user){
        return dao.login(user.getUsername(),user.getPassword());
    }
    public void register(Bbsuser user){
        dao.save(user);

    }
    public int setPagenum(Integer userid,Integer pagenum){
        return dao.setPagenum(userid, pagenum);
    }
    public Bbsuser pic(String userid){
        return dao.pic(Integer.parseInt(userid));
    }
    public int getPagenum(Integer userid){
        return dao.getPagenum(userid);
    }
    public Bbsuser uploadPic(MultipartHttpServletRequest request,CommonsMultipartResolver commonsMultipartResolver){

        commonsMultipartResolver.setDefaultEncoding("utf-8");//上传头像的文件名可以是中文
        commonsMultipartResolver.setResolveLazily(true);
        commonsMultipartResolver.setMaxInMemorySize(1024*1024*4);//设置缓冲
        commonsMultipartResolver.setMaxUploadSizePerFile(1024*1024);//设置每个文件大小
        commonsMultipartResolver.setMaxUploadSize(1024*1024*2);//设置最多能上传文件的大小
        //MultipartHttpServletRequest req=commonsMultipartResolver.resolveMultipart(request);
        MultipartFile multipartFile=request.getFile("file0");//取得头像的文件域
        String fileType=types.get(multipartFile.getContentType());
        //String filename=file.getOriginalFilename();
        UUID uuid= UUID.randomUUID();
        File file=new File("e:/upload/"+uuid+fileType);//新头像文件对象
        Bbsuser user=new Bbsuser();
        try {
            multipartFile.transferTo(file);
            user.setUsername(request.getParameter("reusername"));
            user.setPassword(request.getParameter("repassword"));
            user.setPagenum(5);//默认是每页数据5个
            //byte lob存储 char lob
            FileInputStream fis=new FileInputStream(file);
            byte[] buffer=new byte[fis.available()];//开辟该文件大小的缓冲区
            fis.read(buffer);//把头像读到缓冲区内
            user.setPic(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;

    }

}
