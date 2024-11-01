package tn.seif.ecommerce.config.external;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicOrderConfig {

    @Value("${notification-service.message.destination}")
    private  String orderTopic;

    @Bean
    public NewTopic orderTopic(){
        return  TopicBuilder.name(orderTopic).build();
    }
}
