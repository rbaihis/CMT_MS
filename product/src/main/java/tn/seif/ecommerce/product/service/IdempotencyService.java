package tn.seif.ecommerce.product.service;

import io.lettuce.core.RedisCommandExecutionException;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tn.seif.ecommerce.context.threadlocal.IdempotencyKeyContextManager;
import tn.seif.ecommerce.exception.CustomServerErrorException;
import tn.seif.ecommerce.exception.DuplicatedRequestException;
import tn.seif.ecommerce.exception.NotAcceptedFormatIdempotencyKeyException;
import tn.seif.ecommerce.product.entity.IdempotencyPrefixType;

import java.util.concurrent.TimeUnit;



@Service
@RequiredArgsConstructor
@Slf4j
public class IdempotencyService implements IIdempotencyService {


    private final RedisTemplate<String, String> redisTemplate;


    @Override
    public boolean isRequestProcessed(String idempotencyKey, IdempotencyPrefixType prefixType) {

        return Boolean.TRUE.equals(redisTemplate.hasKey(prefixType + idempotencyKey));
    }

    @Override
    public void markRequestAsProcessed(String idempotencyKey, IdempotencyPrefixType prefixType, long ttlSeconds) {
        redisTemplate.opsForValue().set(prefixType + idempotencyKey, "processed", ttlSeconds, TimeUnit.SECONDS);
    }

    @Override
    public void deleteIdempotencyKeyRequest(String idempotencyKey, IdempotencyPrefixType prefixType) {

        redisTemplate.delete(prefixType + idempotencyKey);
    }

    @Override
    public void deleteThisIdempotencyForAllPrefix(String idempotencyKey) {
        for (IdempotencyPrefixType prefix : IdempotencyPrefixType.values())
            redisTemplate.delete(prefix + idempotencyKey);
    }



    @Override
    public void assertIdempotency(int ttlSeconds, IdempotencyPrefixType prefixType){
        final int UUID_LENGTH = 36;

        // key fetched from context (by filter interceptors from header X-Idempotency-Key)
        String keyIdempotency= IdempotencyKeyContextManager.getIdempotencyKey();

        if(keyIdempotency == null || keyIdempotency.length() != UUID_LENGTH)
            throw new NotAcceptedFormatIdempotencyKeyException("idempotency key do not meet uuid standard");

        try {
            if( isRequestProcessed(keyIdempotency,prefixType) )
                throw new DuplicatedRequestException("purchase product Order Already Executed");
            markRequestAsProcessed(keyIdempotency,prefixType, ttlSeconds);
        }catch (RedisConnectionException | RedisCommandTimeoutException ex){
            log.error("Redis DB problem , on idempotency initial operation (connection failed || timed out)");
            throw new CustomServerErrorException(" Redis DB problem , on idempotency initial operation (connection failed || timed out)");
        }catch (RedisCommandExecutionException ex){
            log.error("Redis DB problem , on idempotency initial operation ( failed to add key || read key  )");
            throw new CustomServerErrorException(" Redis DB problem , on idempotency initial operation ( failed to add key || read key  ) ");
        }
    }


}
