package effectivejava.flawed.chapter02.lab03;

/**
 * FLAWED IMPLEMENTATION - Broken lazy initialization singleton
 * 
 * This attempts lazy initialization but has a race condition.
 * Multiple threads can create multiple instances!
 */
public class LazyConfigManager {

    // Not volatile - visibility issues across threads!
    private static LazyConfigManager instance;

    private final String configSource;
    private boolean loaded;

    private LazyConfigManager() {
        // Simulate expensive initialization
        try {
            Thread.sleep(10); // Simulate loading from file/network
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.configSource = "loaded-at-" + System.currentTimeMillis();
        this.loaded = true;
    }

    // BROKEN: Race condition - multiple threads can enter the if block
    public static LazyConfigManager getInstance() {
        if (instance == null) {
            // Multiple threads can reach here before any assigns instance!
            instance = new LazyConfigManager();
        }
        return instance;
    }

    // This version is thread-safe but has performance issues
    public static synchronized LazyConfigManager getInstanceSync() {
        if (instance == null) {
            instance = new LazyConfigManager();
        }
        return instance;
    }

    // ALSO BROKEN: Naive double-checked locking (before Java 5 memory model fix)
    public static LazyConfigManager getInstanceBrokenDCL() {
        if (instance == null) {
            synchronized (LazyConfigManager.class) {
                if (instance == null) {
                    // Without volatile, this can publish a partially constructed object!
                    instance = new LazyConfigManager();
                }
            }
        }
        return instance;
    }

    public String getConfigSource() {
        return configSource;
    }

    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public String toString() {
        return String.format("LazyConfigManager@%d[source=%s]",
                System.identityHashCode(this), configSource);
    }
}
