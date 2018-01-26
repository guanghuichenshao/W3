package com.zyc.service;

import com.zyc.dao.ArticleDao;
import com.zyc.dao.IArticleDao;
import com.zyc.po.Article;
import com.zyc.po.Message;
import com.zyc.service.kafka.consumer.MsgConsumer;
import com.zyc.service.kafka.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/15 0015.
 */
@Service
@Transactional
public class ArticleService {
    @Autowired
    private IArticleDao dao;
    @Autowired
    private ArticleDao pdao;
    @Autowired
    private MsgProducer pmsg;
    @Autowired
    private MsgConsumer cmsg;
    public Page<Article> queryZ(Pageable pageable){
        return dao.queryZ(pageable);
    }
    public Article save(Article article){
        return dao.save(article);
    }
    public void delete(Integer id){
        dao.delz(id);
    }
    public Map<String,Object> queryByZ(int id){
        return pdao.queryByid(id);

    }
    public Article findone(int id){
        return dao.findOne(id);
    }
    public void sendMsg(Article article){
        pmsg.sendMsg(article);

    }
    public  List<Message> consumerMsg(int uid){
        return cmsg.consumerMsg(uid);
    }



}
