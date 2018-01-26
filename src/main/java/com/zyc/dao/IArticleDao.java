package com.zyc.dao;

import com.zyc.po.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.List;

/**
 * Created by Administrator on 2018/1/15 0015.
 */
public interface IArticleDao extends CrudRepository<Article,Integer >{
    @Query("select a from Article a where a.rootid=0")
    public Page<Article> queryZ(Pageable pageable);
    @Override
    public Article save(Article article);
    @Modifying
    @Query("delete from Article where id=:id or rootid=:id")
    public void delz(@Param("id") Integer id);
    @Query("select a from Article a where id=:id")
    public Article findOne(@Param("id") Integer id);
}
