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
        factory.setConcurrency("5"); //工厂创建并发的消费者（最大并发量为5）
        return factory;
    }

    // //在Topic模式中，对消息的监听需要对containerFactory进行配置
    @Bean("jmsTopicListener")
    public DefaultJmsListenerContainerFactory jmsTopicListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        factory.setPubSubDomain(true); //设置为true 表示为topic (发布订阅)
        //设置事务
        factory.setSessionTransacted(false);
        //手动签收
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return factory;
    }
}
