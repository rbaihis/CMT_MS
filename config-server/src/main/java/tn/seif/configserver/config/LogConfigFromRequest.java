package tn.seif.configserver.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;


import java.io.IOException;

@Order(1)
@Component
public class LogConfigFromRequest extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

//        if (correlationId != null) {
            // Add correlationId to the MDC for logging
            MDC.put("correlationId", correlationId);
//        }

        try {
            // to go the next chain of filter // required -mandatory
            filterChain.doFilter(request, response);
        } finally {
            // Remove correlationId from MDC to prevent it from affecting other requests
            MDC.remove("correlationId");
        }
    }
}