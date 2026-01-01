package effectivejava.flawed.chapter02.lab03;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * FLAWED IMPLEMENTATION - Naive singleton with public static final field
 * 
 * This implementation has several vulnerabilities:
 * 1. Reflection can create additional instances
 * 2. Serialization creates new instances
 * 3. No protection against malicious attacks
 * 
 * Study this code, run SingletonBreaker, and then implement secure versions.
 */
public class ConfigurationManager implements Serializable {

    private static final long serialVersionUID = 1L;

    // Public field singleton - simple but vulnerable
    public static final ConfigurationManager INSTANCE = new ConfigurationManager();

    private final Map<String, String> properties;
    private boolean initialized;

    // Private constructor - but not protected against reflection!
    private ConfigurationManager() {
        this.properties = new HashMap<>();
        loadDefaultConfiguration();
    }

    private void loadDefaultConfiguration() {
        properties.put("app.name", "MyApplication");
        properties.put("app.version", "1.0.0");
        properties.put("db.host", "localhost");
        properties.put("db.port", "5432");
        properties.put("log.level", "INFO");
        initialized = true;
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public int getPropertyCount() {
        return properties.size();
    }

    // MISSING: readResolve() method for serialization protection!
    // This means deserializing creates a NEW instance!

    @Override
    public String toString() {
        return String.format("ConfigurationManager@%d[properties=%d, initialized=%b]",
                System.identityHashCode(this), properties.size(), initialized);
    }
}
