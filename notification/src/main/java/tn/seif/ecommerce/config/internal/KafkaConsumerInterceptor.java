package tn.seif.ecommerce.config.internal;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Headers;
import tn.seif.ecommerce.context.threadlocal.CorrelationIdContextManager;
import tn.seif.ecommerce.context.threadlocal.IdempotencyKeyContextManager;
import tn.seif.ecommerce.context.threadlocal.MdcLogContextManager;

import java.util.Map;

@Slf4j
// todo at application.properties:  kafka.consumer.properties.interceptor.classes=tn.seif.ecommerce.config.internal.KafkaConsumerInterceptor
public class KafkaConsumerInterceptor implements ConsumerInterceptor<String,Object> {

    private void setCorrelationIdContextManager(Headers headers){
        byte[] correlationIdHeader = headers.lastHeader("X-Correlation-Id") != null ? headers.lastHeader("X-Correlation-Id").value() : null;
        if (correlationIdHeader != null) {
            String correlationId = new String(correlationIdHeader);
            CorrelationIdContextManager.setCorrelationId(correlationId);
        }
    }
    private void setIdempotencyKeyManger(Headers headers){
        byte[] idempotencyKeyHeader = headers.lastHeader("X-Idempotency-Key") != null ? headers.lastHeader("X-Idempotency-Key").value() : null;
        if (idempotencyKeyHeader != null) {
            String idempotencyKey = new String(idempotencyKeyHeader);
            IdempotencyKeyContextManager.setIdempotencyKeyThreadLocal(idempotencyKey);
        }
    }

    /**
       this depends on "CorrelationIdContextManager.getCorrelationId();"  value must be executed after set CorrelationId
     **/
    private void setMdcLoggingCorrelationId(Headers headers){
        String correlationId = CorrelationIdContextManager.getCorrelationId();
        if (correlationId != null)
            MdcLogContextManager.setMdcCorrelationId(correlationId);
        }


    @Override
    public ConsumerRecords<String, Object> onConsume(ConsumerRecords<String, Object> records) {
        for (ConsumerRecord<String, Object> record : records) {
            Headers headers = record.headers();

            setCorrelationIdContextManager(headers);
            setIdempotencyKeyManger(headers);
            setMdcLoggingCorrelationId(headers); // depends on setIdempotencyKeyManger(headers);
        }
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> map) {
        String correlationId = CorrelationIdContextManager.getCorrelationId();
        if(correlationId != null) {
            log.info("consumer with correlationId: {} , committed message ",correlationId);
            return;
        }
        log.warn("consumer with correlationId: [message-have-no-correlation-Id] , committed message ");
    }

    @Override
    public void close() {

        CorrelationIdContextManager.clear();
        IdempotencyKeyContextManager.clear();
        MdcLogContextManager.clearMdcCorrelationId();
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
