package com.zyc.dao;

import com.zyc.po.Article;
import com.zyc.po.Bbsuser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/23 0023.
 */
@Repository
public class ArticleDao {
    @PersistenceContext
    private EntityManager entityManager;
    public Map<String,Object> queryByid(int id){
        StoredProcedureQuery storedProcedureQuery=entityManager.createStoredProcedureQuery("p_2");
        storedProcedureQuery.registerStoredProcedureParameter(1,Integer.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter(2,String.class, ParameterMode.OUT);
        storedProcedureQuery.setParameter(1,id);
        storedProcedureQuery.execute();
        List<Object[]> list1=storedProcedureQuery.getResultList();
        List<Article> list=new ArrayList<>();
        list1.forEach((o)->{
            Article a=new Article();
            a.setId(Integer.parseInt(o[0].toString()));
            a.setRootid(Integer.parseInt(o[1].toString()));
            a.setTitle(o[2].toString());
            a.setContent(o[3].toString());
            Bbsuser user=new Bbsuser();
            user.setUserid(Integer.parseInt(o[4].toString()));
            a.setBbsuser(user);
            try {
                java.util.Date d=new SimpleDateFormat("yyyy-MM-dd").parse(o[5].toString());
                a.setDatatime(new java.sql.Date(d.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            list.add(a);


        });
        String title=storedProcedureQuery.getOutputParameterValue(2).toString();

        Map <String,Object>map=new HashMap<>();
        map.put("list",list);
        map.put("title",title);
        return map;

    }
}
