package tn.seif.ecommerce.config.internal;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.RequestContextFilter;
import tn.seif.ecommerce.context.threadlocal.CorrelationIdContextManager;
import tn.seif.ecommerce.context.threadlocal.IdempotencyKeyContextManager;
import tn.seif.ecommerce.context.threadlocal.MdcLogContextManager;

import java.io.IOException;

// todo : use this Filter To set Up cross cutting concern (MDC-log,CorrelationId,IdempotencyKey)

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Slf4j
public class GlobalHttpRequestFilterConfig extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String IDEMPOTENCY_ID_HEADER = "X-Idempotency-Key";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        String idempotencyKey = request.getHeader(IDEMPOTENCY_ID_HEADER);

        CorrelationIdContextManager.setCorrelationId(correlationId);
        IdempotencyKeyContextManager.setIdempotencyKeyThreadLocal(idempotencyKey);

        MdcLogContextManager.setMdcCorrelationId(CorrelationIdContextManager.getCorrelationId());

        try {
            // to go the next chain of filter // required -mandatory
            filterChain.doFilter(request, response);
        } finally {
            // Remove correlationId from MDC to prevent it from affecting other requests this will be executed when thread is done
            MdcLogContextManager.clearMdcCorrelationId();
            CorrelationIdContextManager.clear();
            IdempotencyKeyContextManager.clear();
        }
    }
}