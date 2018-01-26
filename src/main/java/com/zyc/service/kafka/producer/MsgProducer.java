package com.zyc.service.kafka.producer;

import com.alibaba.fastjson.JSONObject;
import com.zyc.po.Article;
import com.zyc.po.Message;
import com.zyc.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/1/25 0025.
 */
@Component
public class MsgProducer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private ArticleService service;

    public void sendMsg(Article article){
        Message msg=new Message();
        Article zt=service.findone(article.getRootid());//找到主贴
        msg.setZid(String.valueOf(zt.getBbsuser().getUserid()));//发主贴的用户id
        msg.setRid(String.valueOf(article.getBbsuser().getUserid()));//发当前从贴的用户id
        msg.setTitle(zt.getTitle());//主贴title
        msg.setRtitle(article.getTitle());
        String topic="reply"+msg.getZid();//针对哪个内容发消息，我们定义针对发主贴的用户回复从贴

        kafkaTemplate.send(topic, JSONObject.toJSONString(msg));


    }
}
