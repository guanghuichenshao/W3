package com.zyc.service.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.zyc.po.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2018/1/25 0025.
 */
@Component
public class MsgConsumer {
    //以下读取消费者属性值
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String servers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;


    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;


    @Value("${spring.kafka.consumer.auto-commit-interval-ms}")
    private String autoCommitInterval;


    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;


    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value("${spring.kafka.consumer.session-timeout-ms}")
    private String sessionTimeoutMs;

    private static KafkaConsumer<String, String> consumer;

    public  KafkaConsumer<String, String>  createConsumer(){
        if (consumer == null) {

            Properties props=new Properties();

            ////Kafka集群连接串，可以由多个host:port组成
            props.put("bootstrap.servers", this.servers);

            //Consumer的group id，同一个group下的多个Consumer不会拉取到重复的消息，不同group下的Consumer则会保证拉取到每一条消息。注意，同一个group下的consumer数量不能超过分区数。必须要使用别的组名称， 如果生产者和消费者都在同一组，则不能访问同一组内的topic数据
            props.put("group.id", this.groupId);
            //是否自动周期性提交已经拉取到消费端的消息offset 接收到 肯定能接收到，但是如果设置是false
            //那么你就必须手动提交，否则，他会把以前没消费的数据一起取出来，如果自动提交。之后将不再看到重复数据

            //是否自动提交已拉取消息的offset。提交offset即视为该消息已经成功被消费，
            // 该组下的Consumer无法再拉取到该消息（除非手动修改offset）。默认为true
            props.put("enable.auto.commit", this.enableAutoCommit);
            //原由是Kafka新的消费者，默认情况下会从最后一条消费进行消费，就是开始消费的时候，
            // 会从新增加的消息开始处理，即从我开始添加的1000条以后，才会开始处理。
            //所以必须要设置auto.offset.reset设置新加入的消费者，从头条开始处理消费。当然有些情况，
            // 可能需要从最新的开始处理。
            props.put("auto.offset.reset", this.autoOffsetReset);
            //自动提交offset的间隔毫秒数，默认5000。
            //本 例中采用的是自动提交offset，Kafka client会启动一个线程定期将offset提交至broker。假设在自动提交的间隔内发生故障（比如整个JVM进程死掉），那么有一部分消息是会被 重复消费的。要避免这一问题，可使用手动提交offset的方式。构造consumer时将enable.auto.commit设为false，并在代 码中用consumer.commitSync()来手动提交。
            props.put("auto.commit.interval.ms", this.autoCommitInterval);

            props.put("session.timeout.ms", this.sessionTimeoutMs);
            props.put("key.deserializer", this.keyDeserializer);
            props.put("value.deserializer", this.valueDeserializer);
            consumer=new KafkaConsumer<String, String>(props);
        }

        return consumer;
    }

    public synchronized List<Message> consumerMsg(int uid){//消费消息，uid：当前用户的id，也就是只有发主贴的用户才能够消费消息
        consumer=this.createConsumer();
        consumer.subscribe(Arrays.asList("reply"+uid));
        List<Message> list=new ArrayList<Message>();
        synchronized (this){
            ConsumerRecords<String, String> crs= consumer.poll(100);

            for(ConsumerRecord<String,String> cr:crs){
                System.out.println("收到了数据-》》》》》》》》》》》》 " + cr.partition() + ", offset: " + cr.offset() + ",key:" + cr.key() + ", message: " + cr.value());
                Message m= JSON.parseObject(cr.value(),Message.class);
                list.add(m);
            }
            consumer.commitAsync();//代表当前数据已经消费，生产者就不再发送了
        }
        return list;

    }
}
