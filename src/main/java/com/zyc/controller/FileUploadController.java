package com.zyc.controller;

import com.zyc.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2018/1/21 0021.
 */
@Controller
@RequestMapping(value = {"/kindupload2"})
public class FileUploadController {
    @Autowired
    private FileUploadService service;
    @RequestMapping(value = {"/up"})
    public void upload(MultipartHttpServletRequest request, HttpServletResponse response){
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getServletContext());
        String str="";
        if(commonsMultipartResolver.isMultipart(request)){
            str=service.fileUpload(request,commonsMultipartResolver);
        }
        PrintWriter out= null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(str);
        out.flush();
        out.close();

    }
}
