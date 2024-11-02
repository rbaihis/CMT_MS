package tn.seif.ecommerce.context.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class MdcLogContextManager {
    private static final String MDC_KEY_CORRELATION_NAME = "correlationId";
    private static final String MDC_KEY_IDEMPOTENCY_KEY_NAME = "idempotencyKey";

    public static void setMdcCorrelationId(String value) {
        MDC.put(MDC_KEY_CORRELATION_NAME, value);
    }
    public static void setMdcKeyIdempotencyKe(String value) {
        MDC.put(MDC_KEY_IDEMPOTENCY_KEY_NAME, value);
    }

    public static void clearMdcCorrelationId() {
        log.debug("Clearing MDC.logging added stuff ");
        MDC.clear();
    }

}
