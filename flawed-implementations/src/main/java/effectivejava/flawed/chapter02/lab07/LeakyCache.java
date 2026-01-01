package effectivejava.flawed.chapter02.lab07;

import java.util.HashMap;
import java.util.Map;

/**
 * FLAWED IMPLEMENTATION - Cache with unbounded memory growth
 * 
 * This cache never removes entries, leading to unbounded memory growth.
 * In long-running applications, this will eventually cause OutOfMemoryError.
 */
public class LeakyCache<K, V> {

    // Regular HashMap - entries are never removed!
    private final Map<K, V> cache = new HashMap<>();
    private long hitCount = 0;
    private long missCount = 0;

    /**
     * Get a value from the cache.
     */
    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
            hitCount++;
        } else {
            missCount++;
        }
        return value;
    }

    /**
     * Put a value in the cache.
     * PROBLEM: Nothing ever removes old entries!
     */
    public void put(K key, V value) {
        cache.put(key, value);
        // Entry stays forever, even if key is no longer used anywhere else
    }

    /**
     * Compute if absent - commonly used for memoization.
     * Same problem: never evicts!
     */
    public V computeIfAbsent(K key, java.util.function.Function<K, V> loader) {
        V value = cache.get(key);
        if (value == null) {
            missCount++;
            value = loader.apply(key);
            cache.put(key, value);
        } else {
            hitCount++;
        }
        return value;
    }

    public int size() {
        return cache.size();
    }

    public long getHitCount() {
        return hitCount;
    }

    public long getMissCount() {
        return missCount;
    }

    public double getHitRate() {
        long total = hitCount + missCount;
        return total == 0 ? 0.0 : (double) hitCount / total;
    }

    /**
     * Manual clear - but caller must remember to call it!
     * And when? Based on what policy?
     */
    public void clear() {
        cache.clear();
    }

    // MISSING:
    // - Size limit with eviction policy (LRU, LFU, FIFO)
    // - Time-based expiration
    // - WeakReference keys for GC-based cleanup
    // - Soft reference values for memory-pressure cleanup
}
