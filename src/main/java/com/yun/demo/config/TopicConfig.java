package com.yun.demo.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class TopicConfig {

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("my-topic");
    }
}
