package com.yun.demo.config;

import org.apache.activemq.command.ActiveMQMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class Consumer {


    @JmsListener(destination = "myqueue", containerFactory = "jmsQueueListener")
    public void listenQueue(ActiveMQMessage message, Session session) throws JMSException {
        System.out.println("listenQueue监听到myqueue的消息" + message);
        //session.recover();
        System.out.println("----------------------------");

    }

    @JmsListener(destination = "myqueue", containerFactory = "jmsQueueListener")
    public void listenQueue1(ActiveMQMessage message, Session session) throws JMSException {
        //先获取当前消息的messageId
        TextMessage msg = (TextMessage) message;
        String jmsMessageID = msg.getJMSMessageID();
        //消费时 先从redis中my-delay-queue 获取当前消息的messageId 如果能获取到则消费  获取不到则表示该消息已经被消费
        System.out.println("listenQueue1 - 监听到myqueue的消息" + msg + "---" + jmsMessageID);
        //session.recover();
        System.out.println("----------------------------");
    }

    @JmsListener(destination = "my-delay-queue", containerFactory = "jmsQueueListener")
    public void listenDelayQueue(ActiveMQMessage message, Session session) {
        System.out.println("监听到my-delay-queue的消息" + message);
    }

    @JmsListener(destination = "my-topic",containerFactory = "jmsTopicListener")
    public void listenTopic(ActiveMQMessage message, Session session) throws JMSException {
        try{
            System.out.println("监听到my-topic的消息"+message);
        }catch (Exception e){
            session.rollback();
        }
    }

}
