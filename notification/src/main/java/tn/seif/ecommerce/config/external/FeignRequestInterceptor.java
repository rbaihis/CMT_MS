package tn.seif.ecommerce.config.external;//package tn.seif.ecommerce.config.external;
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import tn.seif.ecommerce.context.CorrelationIdContextManager;
//import tn.seif.ecommerce.context.IdempotencyKeyContextManager;
//
//// todo:  Use this if REQUEST-INTERCEPTOR if you use OPEN-FEIGN for ClientAPIS calls (CorrelationIds,MDC-LOG,IdempotencyKey)
//@Component
//@Slf4j
//public class FeignRequestInterceptor implements RequestInterceptor {
//
//    public final String IDEMPOTENCY_KEY_HEADER= "X-Idempotency-Key";
//    public final String CORRELATION_ID_HEADER= "X-correlation-Id";
//
//    @Override
//    public void apply(RequestTemplate template) {
//        // todo stopped working suddenly
//        // fetching attributes from current incoming request
//        // RequestContextHolder-Bean must be configured
////        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
////        if (attributes == null) {
////            log.warn("RequestContextHolder.requestAttributes is null --> possible reason current main RequestContextFilter-Bean Not Configured, or u re using direct client request that didn't set the headers");
////            return;
////        }
//
//        String correlationId = CorrelationIdContextManager.getCorrelationId();
//        if (correlationId != null)
//            template.header("X-Correlation-Id", correlationId);
//        else
//            log.warn("X-Correlation-Id is missing in the request attributes");
//
//        String idempotencyKey = IdempotencyKeyContextManager.getIdempotencyKey();
//        if (idempotencyKey != null)
//            template.header("X-Idempotency-Key", idempotencyKey);
//        else
//            log.warn("X-Idempotency-Key is missing in the request attributes");
//
//
//    }
//
//}