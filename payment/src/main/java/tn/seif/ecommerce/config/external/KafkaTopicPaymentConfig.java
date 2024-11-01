package tn.seif.ecommerce.config.external;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaTopicPaymentConfig {
    @Value("${notification-service.message.destination}")
    private String topic;

    @Bean
    public NewTopic paymentTopic(){
        return TopicBuilder.name(topic).build();
    }

}
