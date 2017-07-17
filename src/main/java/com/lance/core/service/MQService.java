package com.lance.core.service;

/**
 * Author Lance.
 * Date: 2017-07-17 16:07
 * Desc:mq服务
 */
public interface MQService {


    /**
     * @param topic   topic名称
     * @param message 消息内容
     * @desc:发送消息
     * @author lance
     * @time: 2017-07-17 16:08:12
     */
    void send(String topic, String message);

    /**
     * @param topic       topic名称
     * @param message     消息内容
     * @param deliverTime 延时 ms
     * @desc:发送消息
     * @author lance
     * @time: 2017-07-17 16:08:12
     */
    void send(String topic, String message, long deliverTime);


    /**
     * @desc:消费
     * @author lance
     * @time: 2017-07-17 16:20:55
     */
    void subscribe(String topic, MQListener listener);


}
