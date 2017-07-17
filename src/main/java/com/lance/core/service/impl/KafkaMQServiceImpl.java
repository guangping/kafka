package com.lance.core.service.impl;

import com.lance.core.service.MQListener;
import com.lance.core.service.MQService;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Author Lance.
 * Date: 2017-07-17 16:22
 * Desc:
 */
public class KafkaMQServiceImpl implements MQService {

    private static final Logger logger = LogManager.getLogger(KafkaMQServiceImpl.class);


    /**
     * @desc:初始化配置
     * @author lance
     * @time: 2017-07-17 17:06:55
     */
    private Properties initProducerConfig() {

        Properties props = new Properties();
        // 此处配置的是kafka的broker地址:端口列表
        props.put("metadata.broker.list", "118.178.91.207:9092");
        //配置value的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //配置key的序列化类
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        //request.required.acks
        //0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
        //1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
        //-1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
        props.put("request.required.acks", "-1");
        return props;
    }


    public void send(String topic, String message) {
        logger.info("发送消息:topic->{},内容->{}", topic, message);
        Properties props = this.initProducerConfig();
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        ProducerRecord record = new ProducerRecord(topic, message);
        producer.send(record);
       /* try {
            RecordMetadata metadata= future.get(1000,TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }*/
    }


    public void send(String topic, String message, long deliverTime) {
        logger.info("发送消息:topic->{},内容->{},延时->{}", topic, message, deliverTime);
        if (deliverTime < 0) {
            throw new RuntimeException("延时时间不能<0");
        }

    }


    private Properties initConsumerConfig() {
        Properties props = new Properties();

        // zookeeper 配置
        props.put("zookeeper.connect", "118.178.91.207:2181");

        // 消费者所在组
        props.put("group.id", "testgroup");

        // zk连接超时
        props.put("zookeeper.session.timeout.ms", "4000");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        // 序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");

        return props;
    }

    public void subscribe(String topic, MQListener listener) {
        Properties properties = this.initConsumerConfig();
        KafkaConsumer consumer = new KafkaConsumer(properties);
        List<String> topics = new ArrayList();
        topics.add(topic);
        consumer.subscribe(topics, new ConsumerRebalanceListener() {
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {

            }

            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

            }
        });
    }
}
