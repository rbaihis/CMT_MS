package tn.seif.ecommerce.product.service;

import tn.seif.ecommerce.product.entity.IdempotencyPrefixType;

public interface IIdempotencyService {





    boolean isRequestProcessed(String idempotencyKey, IdempotencyPrefixType prefixType);

    void markRequestAsProcessed(String idempotencyKey, IdempotencyPrefixType prefixType, long ttlSeconds);

    void deleteIdempotencyKeyRequest(String idempotencyKey, IdempotencyPrefixType prefixType);

    void deleteThisIdempotencyForAllPrefix(String idempotencyKey);

    void assertIdempotency(int ttlSeconds, IdempotencyPrefixType prefixType);
}
