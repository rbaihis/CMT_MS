package tn.seif.ecommerce.config.external;//package tn.seif.ecommerce.config.external;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpRequest;
//import org.springframework.http.client.ClientHttpRequestExecution;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.client.ClientHttpResponse;
//import org.springframework.web.client.RestTemplate;
//import tn.seif.ecommerce.context.threadlocal.CorrelationIdContextManager;
//import tn.seif.ecommerce.context.threadlocal.IdempotencyKeyContextManager;
//
//import java.io.IOException;
//import java.util.Collections;
//
//
////// todo:  Use this if REQUEST-INTERCEPTOR if you use REST-TEMPLATE for ClientAPIS calls (CorrelationIds,MDC-LOG,IdempotencyKey)
//
//
//@Configuration
//@Slf4j
//public class RestTemplateConfigWithInterceptor {
//
//    @Bean
//    public RestTemplate restTemplate(){
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setInterceptors(Collections.singletonList(new RestTemplateHeaderModifierInterceptor()));
//        return restTemplate;
//    }
//
//    private static class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
//
//        public final String IDEMPOTENCY_KEY_HEADER= "X-Idempotency-Key";
//        public final String CORRELATION_ID_HEADER= "X-correlation-Id";
//
//        @Override
//        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
//
//            String correlationId = CorrelationIdContextManager.getCorrelationId();
//            if (correlationId != null)
//                request.getHeaders().add(CORRELATION_ID_HEADER, correlationId);
//            else
//                log.warn("X-Correlation-Id is missing in the request attributes");
//
//            String idempotencyKey = IdempotencyKeyContextManager.getIdempotencyKey();
//            if (idempotencyKey != null)
//                request.getHeaders().add(IDEMPOTENCY_KEY_HEADER, idempotencyKey);
//            else
//                log.warn("X-Idempotency-Key is missing in the request attributes");
//
//            return execution.execute(request, body);
//        }
//    }
//}
