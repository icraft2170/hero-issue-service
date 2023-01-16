package me.userservice.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Supplier

@Component
class CoroutineCacheManager<T> {
    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()
    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache[key] = CacheWrapper(cache = value, Instant.now().plusMillis(ttl.toMillis()))
    }
    suspend fun awaitEvict(key: String) {
        localCache.remove(key)
    }

    suspend fun awaitGetOrPut(
        key: String,
        ttl: Duration = Duration.ofMinutes(5),
        supplier: suspend () -> T
    ): T {
        val now = Instant.now()
        val cacheWrapper = localCache[key]

        val cached = if (cacheWrapper == null) {
            CacheWrapper(cache = supplier(), ttl = now.plusMillis(ttl.toMillis())).also {
                localCache[key] = it
            }
        } else if (now.isAfter(cacheWrapper.ttl)) {
            // 캐시 ttl 초과
            localCache.remove(key)
            CacheWrapper(cache = supplier(), ttl = now.plusMillis(ttl.toMillis())).also {
                localCache[key] = it
            }
        } else {
            cacheWrapper
        }

        checkNotNull(cached.cache)
        return cached.cache
    }

    data class CacheWrapper<T>( val cache: T, val ttl: Instant)
}