package tn.seif.ecommerce.config.external;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import tn.seif.ecommerce.context.threadlocal.CorrelationIdContextManager;

import java.util.Map;
import java.util.UUID;

@Slf4j 
// todo add interceptor in application.properties ==> spring.kafka.producer.properties.interceptor.classes= tn.seif.ecommerce.config.external.KafkaProducerInterceptor
public class KafkaProducerInterceptor implements ProducerInterceptor<String, Object> {

    private String getIdempotencyKey() {
        return UUID.randomUUID().toString(); // Simple example of generating a new one
    }
    private String getCorrelationId() {
        String correlationId;
        if(CorrelationIdContextManager.getCorrelationId()!=null)
            return  CorrelationIdContextManager.getCorrelationId();

        return UUID.randomUUID().toString();
    }

    @Override
    public ProducerRecord<String, Object> onSend(ProducerRecord<String, Object> record) {

        log.info("...... producer message interceptor executed .......");

        // Adding the correlationId and idempotencyKey to the headers
        String correlationId = getCorrelationId();
        String idempotencyKey = getIdempotencyKey();

        // Add these headers to the record
        record.headers().add("X-Correlation-Id", correlationId.getBytes());
        record.headers().add("X-Idempotency-Key", idempotencyKey.getBytes());
        // ps: not your job- to clear context only the one who initiated the context is the one to close it

        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(e!=null){
            log.warn("ACK Failed, with correlationId: {} , with Exc-message: {}",getCorrelationId(),e.getMessage());
        }

        log.info("Acked published with correlationId: {}",getCorrelationId());
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
