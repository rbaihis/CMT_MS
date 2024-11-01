//package tn.seif.ecommerce.config.internal;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerInterceptor;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.OffsetAndMetadata;
//import org.apache.kafka.common.TopicPartition;
//import org.apache.kafka.common.header.Headers;
//import tn.seif.ecommerce.context.threadlocal.CorrelationIdContextManager;
//

////todo USER this filter if you use Kafka Consumer

//import java.util.Map;
//
//@Slf4j
//// todo at application.properties:  kafka.consumer.properties.interceptor.classes=tn.seif.ecommerce.config.internal.KafkaConsumerInterceptor
//public class KafkaConsumerInterceptor implements ConsumerInterceptor<String,Object> {
//    @Override
//    public ConsumerRecords<String, Object> onConsume(ConsumerRecords<String, Object> records) {
//        log.info("Consumer .....  message interceptor executed .......");
//
//
//        for (ConsumerRecord<String, Object> record : records) {
//            Headers headers = record.headers();
//            if (headers != null) {
//                byte[] correlationIdHeader = headers.lastHeader("X-Correlation-Id") != null ? headers.lastHeader("X-Correlation-Id").value() : null;
//                if (correlationIdHeader != null) {
//                    String correlationId = new String(correlationIdHeader);
//                    CorrelationIdContextManager.setCorrelationId(correlationId);
//
//                }
//            }
//        }
//        return records;
//    }
//
//    @Override
//    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
//        String correlationId = CorrelationIdContextManager.getCorrelationId();
//
//        if(correlationId != null) {
//            log.info("consumer with correlationId: {} , committed message ",correlationId);
//            return;
//        }
//        log.warn("consumer with correlationId: [message-have-no-correlation-Id] , committed message ");
//    }
//
//    @Override
//    public void close() {
//        CorrelationIdContextManager.clear();
//    }
//
//    @Override
//    public void configure(Map<String, ?> map) {
//
//    }
//}
