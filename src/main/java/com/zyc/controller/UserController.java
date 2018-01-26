package com.zyc.controller;

import com.zyc.po.Bbsuser;
import com.zyc.service.BBSUserService;
import com.zyc.util.FreemarkerUtils;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
@Controller
@RequestMapping(value = {"/user"})
public class UserController {
    @Autowired
    private BBSUserService service;
    @RequestMapping(value = {"/pic/{userid}"})
    public void pic(@PathVariable String userid,HttpServletResponse response){
        Bbsuser user=service.pic(userid);
        try {
            OutputStream outputStream=response.getOutputStream();
            outputStream.write(user.getPic());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @RequestMapping(value = {"/row/{userid}/{pagenum}"})
    public void setRow(@PathVariable Integer userid,
                       HttpServletRequest request,HttpServletResponse response,@PathVariable Integer pagenum){
        service.setPagenum(userid,pagenum);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/article/queryz/1");

        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(value = {"/reg"})

    public void reg(HttpServletResponse response, MultipartHttpServletRequest request){
        CommonsMultipartResolver commonsMultipartResolver=new CommonsMultipartResolver(request.getServletContext());
        Bbsuser user=null;
        if(commonsMultipartResolver.isMultipart(request)){
            user=service.uploadPic(request,commonsMultipartResolver);
            service.register(user);
        }
        //FreemarkerUtils.forward("show",null,response);
        RequestDispatcher dispatcher=request.getRequestDispatcher("/article/queryz/1");

        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    @RequestMapping(value = {"/logout"})

    public void loginout(HttpServletResponse response,HttpServletRequest request){
        request.getSession().removeAttribute("userid");
        request.getSession().removeAttribute("username");
        RequestDispatcher dispatcher=request.getRequestDispatcher("/article/queryz/1");

        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //FreemarkerUtils.forward("show",null,response);
    }
    @RequestMapping(value = {"/login/{username}/{password}/{sun}"})
    public void login(@PathVariable String username,@PathVariable String password,@PathVariable String sun,
            HttpServletResponse response,HttpServletRequest request,ModelMap model){
        String flag="0";
        //Map map=new HashMap();
        Bbsuser user=new Bbsuser();
        user.setUsername(username);
        user.setPassword(password);
        Bbsuser user1=service.login(user);
        if(user1!=null){
            request.getSession().setAttribute("username",user1.getUsername());
            request.getSession().setAttribute("userid",user1.getUserid());

            //map.put("username",user1.getUsername());
            //map.put("userid",user1.getUserid());


            if(sun.equals("on")){//勾选7天
                Cookie cookie=new Cookie("papaoku",user.getUsername());
                cookie.setMaxAge(3600*24*7);

                Cookie cookie1=new Cookie("papaokp",user.getPassword());
                cookie1.setMaxAge(3600*24*7);
                response.addCookie(cookie);
                response.addCookie(cookie1);
            }
            //model.put("user",user);
            flag=String.valueOf(user1.getUserid());

        }else{
            //map.put("username",null);

        }
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");


        try {
            PrintWriter out=response.getWriter();
            out.print(flag);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        request.getSession().setAttribute("username",user1.getUsername());
//        request.getSession().setAttribute("userid",user1.getUserid());
//        //request.getSession().setAttribute("pagenum",user1.getPagenum());
//        RequestDispatcher dispatcher=request.getRequestDispatcher("/article/queryz/1");
//
//        try {
//            dispatcher.forward(request,response);
//        } catch (ServletException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
