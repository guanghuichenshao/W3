package com.zyc.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyc.po.Article;
import com.zyc.po.Bbsuser;
import com.zyc.po.Message;
import com.zyc.po.PageBean;
import com.zyc.service.ArticleService;
import com.zyc.service.BBSUserService;
import com.zyc.util.FreemarkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/15 0015.
 */
@Controller
@RequestMapping(value = {"/article"})
public class ArticleController {
    @Autowired
    private ArticleService service;
    @Autowired
    private BBSUserService uservice;

    @RequestMapping(value = {"/queryz/{cupage}"})
    public void queryZT(HttpServletResponse response, HttpServletRequest request, @PathVariable String cupage) {
        int currentPage = Integer.parseInt(cupage);
        int size = 0;
        if (request.getSession().getAttribute("username") == null) {
            size = 5;
        } else {
            int userid = Integer.parseInt(request.getSession().getAttribute("userid").toString());
            size = uservice.getPagenum(userid);
            //size=Integer.parseInt(request.getSession().getAttribute("pagenum").toString());
        }
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(currentPage - 1, size, sort);
        Page<Article> page = service.queryZ(pageable);
        PageBean pb = new PageBean();
        pb.setCurrentPage(currentPage);
        pb.setData(page.getContent());
        pb.setMaxPage(page.getTotalPages());
        pb.setMaxRow(page.getTotalElements());
        pb.setRowPerPage(page.getNumberOfElements());
        Map<String, Object> map = new HashMap<>();
        map.put("pb", pb);
        map.put("userid", request.getSession().getAttribute("userid"));
        map.put("username", request.getSession().getAttribute("username"));
        FreemarkerUtils.forward("show", map, response);

    }

    @RequestMapping(value = {"/saveZ"})
    public void saveZ(@ModelAttribute Article article, HttpServletRequest request, HttpServletResponse response) {
        System.out.print("ok");
        article.setDatatime(new Date(System.currentTimeMillis()));
        article.setRootid(0);
        if (service.save(article) != null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/article/queryz/1");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @RequestMapping(value = {"/saveC"})
    public void saveC(@RequestParam("title") String title,
                      @RequestParam("content") String content,
                      @RequestParam("rootid") int rootid,
                      @RequestParam("userid") int userid, HttpServletRequest request, HttpServletResponse response) {
        Article article=new Article();
        article.setTitle(title);
        article.setContent(content);
        Bbsuser user=new Bbsuser();
        user.setUserid(userid);
        article.setRootid(rootid);//本操作是增加从贴，rootid代表该贴从属于哪个主贴
        article.setBbsuser(user);
        article.setDatatime(new Date(System.currentTimeMillis()));
        if (service.save(article) != null) {
            service.sendMsg(article);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/article/queryc/"+rootid);
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @RequestMapping(value={"/con/{uid}"})
    public void consumerMsg(@PathVariable String uid,HttpServletResponse response){
        List<Message> list=service.consumerMsg(Integer.parseInt(uid));
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        String json=JSONObject.toJSONString(list);
        try {
            PrintWriter out=response.getWriter();
            out.println(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping(value = {"/queryc/{id}"})
    public void queryByZ(@PathVariable int id,HttpServletResponse response){
        Map<String,Object> map=service.queryByZ(id);
        String json= JSONObject.toJSONString(map,true);
        System.out.println(json);
        try {
            PrintWriter out=response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RequestMapping(value = {"/delz/{id}"})
    public void delz(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) {
        service.delete(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/article/queryz/1");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}