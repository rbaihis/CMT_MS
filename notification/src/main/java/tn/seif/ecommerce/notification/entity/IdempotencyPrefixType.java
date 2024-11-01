package tn.seif.ecommerce.notification.entity;


public enum IdempotencyPrefixType {
    ORDER("idempotency:order:"),
    PAYMENT("idempotency:purchase:cancel:");

    private final String prefix;

    IdempotencyPrefixType(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return prefix;
    }
}
