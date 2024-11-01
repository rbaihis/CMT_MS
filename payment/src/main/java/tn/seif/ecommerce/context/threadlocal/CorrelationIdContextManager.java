package tn.seif.ecommerce.context.threadlocal;

public class CorrelationIdContextManager {
    private static final ThreadLocal<String> correlationIdThreadLocal = new ThreadLocal<>();

    public static void setCorrelationId(String correlationId) {
        correlationIdThreadLocal.set(correlationId);
    }

    public static String getCorrelationId() {
        return correlationIdThreadLocal.get();
    }

    public static void clear() {
        correlationIdThreadLocal.remove();
    }
}
