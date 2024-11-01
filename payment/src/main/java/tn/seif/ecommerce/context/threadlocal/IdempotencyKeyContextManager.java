package tn.seif.ecommerce.context.threadlocal;

public class IdempotencyKeyContextManager {
    private static final ThreadLocal<String> idempotencyKeyThreadLocal = new ThreadLocal<>();

    public static void setIdempotencyKeyThreadLocal(String value) {
        idempotencyKeyThreadLocal.set(value);
    }

    public static String getIdempotencyKey() {
        return idempotencyKeyThreadLocal.get();
    }

    public static void clear() {
        idempotencyKeyThreadLocal.remove();
    }
}
