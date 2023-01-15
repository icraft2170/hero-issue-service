package me.userservice.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Component
class CoroutineCacheManager<T> {
    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()
    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache[key] = CacheWrapper(cache = value, Instant.now().plusMillis(ttl.toMillis()))
    }
   data class CacheWrapper<T>( val cache: T, val ttl: Instant)
}