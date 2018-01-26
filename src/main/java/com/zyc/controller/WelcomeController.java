package com.zyc.controller;

import com.zyc.util.FreemarkerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/11 0011.
 */
@Controller
@RequestMapping(value = {"/welcome"})
public class WelcomeController {
    @RequestMapping(value = {"/index"})
    public void index(HttpServletResponse response, HttpServletRequest request){
        //Map map=new HashMap();
        RequestDispatcher dispatcher=request.getRequestDispatcher("/article/queryz/1");
        try {
            dispatcher.forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //FreemarkerUtils.forward("show",map,response);
    }
}


