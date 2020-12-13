package com.yun.demo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.Session;

@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerURL;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
        return connectionFactory;
    }

    // 在Queue模式中，对消息的监听需要对containerFactory进行配置
    @Bean(name = "jmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
//        redeliveryPolicy.setMaximumRedeliveries(3); //消息重发策略  默认重发 6 次后，消息就会进入死信队列 这里设置3次
//        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setPubSubDomain(false); //设置为false表示为队列
        //设置事务
        factory.setSessionTransacted(Boolean.TRUE);
        //手动签收
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        //设置并发数
        //factory.setConcurrency("5"); //工厂创建并发的消费者（最大并发量为5）
        return factory;
    }

    // //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("jmsTopicListener")
    public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setPubSubDomain(true); //设置为true 表示为topic (发布订阅)
        //设置事务
        factory.setSessionTransacted(true);
        //开启持久化订阅
        factory.setSubscriptionDurable(true);
        /**
         * 非持久化订阅  只有在线才可以接受消息
         * 持久化订阅  订阅后需要发一次消息生效
         *              在线可以接受消息
         *              不在线的话 等待再次上线 可以收到之前未收到的消息 -- 但是消息必须是持久化的
         */
        //手动签收
        factory.setClientId("id2");
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return factory;
    }
}
