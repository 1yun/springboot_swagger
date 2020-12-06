package com.yun.demo.config;

import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    @Qualifier("delayQueue")
    private Queue delayQueue;

    //@Scheduled(fixedRateString = "3000")  //每3秒发送一次消息
    public void send() {
        ConnectionFactory connectionFactory = jmsMessagingTemplate.getConnectionFactory();
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
            // 创建一个消息队列
            String msg = (UUID.randomUUID() + "").substring(0, 6);
            Destination destin = session.createQueue("myqueue");
            producer = session.createProducer(destin);
            //producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage textMessage = session.createTextMessage(msg);
            textMessage.setJMSMessageID(UUID.randomUUID().toString()); //多台机器可以用雪花算法
            String jmsMessageID = textMessage.getJMSMessageID();
            //使用redis的hash存入唯一messageId hset my-delay-queue jmsMessageID  保证消息幂等性
            System.out.println("生产者生产消息" + msg + jmsMessageID);
            //textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayTime);
            producer.send(textMessage);
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                try {
                    session.rollback();
                } catch (JMSException e1) {
                }
            }

        } finally {
            if (producer != null) {
                try {
                    producer.close();
                } catch (JMSException e) {
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                }
            }
        }
    }

    public void sendDelayQueue() {

        System.out.println("-------延时队列准备发消息----");
        Map<String, Object> headers = new HashMap<>();
        headers.put(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10000);
        String msg = (UUID.randomUUID() + "").substring(0, 6);
        jmsMessagingTemplate.convertAndSend(delayQueue, msg, headers);
        System.out.println("-------延时队列发消息完成----" + msg);

    }
}
