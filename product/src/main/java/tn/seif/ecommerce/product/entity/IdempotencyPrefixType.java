package tn.seif.ecommerce.product.entity;


public enum IdempotencyPrefixType {
    PURCHASE("idempotency:purchase:"),
    PURCHASE_CANCEL("idempotency:purchase:cancel:");

    private final String prefix;

    IdempotencyPrefixType(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return prefix;
    }
}
