package effectivejava.labs.chapter02.lab03;

/**
 * ============================================================================
 * LAB 03: Enforce Singleton with Private Constructor or Enum (Item 3)
 * ============================================================================
 * Chapter 2, pp. 17-19
 * 
 * SCENARIO:
 * You have a ConfigurationManager that should have only ONE instance.
 * The current implementations are VULNERABLE to attacks!
 * 
 * PROBLEMS WITH CURRENT APPROACH:
 * 1. Public field singleton can be broken via reflection
 * 2. Lazy initialization has race conditions
 * 3. Serialization creates new instances
 * 
 * YOUR TASK:
 * TODO #1: Create an ENUM singleton (recommended by Bloch!)
 * TODO #2: If using static factory, add readResolve() for serialization
 * TODO #3: If using lazy init, use double-checked locking correctly
 * 
 * WHY ENUM IS BEST:
 * - Free serialization safety
 * - Free reflection safety  
 * - Concise and clear
 * - JVM guarantees single instance
 * ============================================================================
 */
public class ConfigurationManager {

    // =========================================================================
    // FLAWED APPROACH 1: Public static final field
    // Can be broken by reflection!
    // =========================================================================
    
    public static final ConfigurationManager INSTANCE = new ConfigurationManager();

    private String environment = "development";
    private int maxConnections = 10;
    private boolean debugMode = true;

    // FIXME: This constructor can be called via reflection!
    // AccessibleObject.setAccessible() bypasses private
    private ConfigurationManager() {
        // TODO: Add defense against reflection attack:
        // if (INSTANCE != null) {
        //     throw new IllegalStateException("Already instantiated!");
        // }
    }

    public String getEnvironment() { return environment; }
    public void setEnvironment(String env) { this.environment = env; }
    public int getMaxConnections() { return maxConnections; }
    public void setMaxConnections(int max) { this.maxConnections = max; }
    public boolean isDebugMode() { return debugMode; }
    public void setDebugMode(boolean debug) { this.debugMode = debug; }

    // FIXME: Missing readResolve() means deserialization creates new instance!
    // TODO: Add this method to preserve singleton during serialization:
    // private Object readResolve() {
    //     return INSTANCE;
    // }

    @Override
    public String toString() {
        return String.format("Config{env='%s', maxConn=%d, debug=%b}",
                environment, maxConnections, debugMode);
    }

    // =========================================================================
    // TODO: Create the ENUM singleton version (BEST APPROACH)
    // =========================================================================
    
    // public enum ConfigManager {
    //     INSTANCE;
    //     
    //     private String environment = "development";
    //     // ... other fields
    //     
    //     public String getEnvironment() { return environment; }
    //     // ... other methods
    // }
    //
    // Usage: ConfigManager.INSTANCE.getEnvironment()

    public static void main(String[] args) throws Exception {
        System.out.println("=== Singleton Test ===\n");
        
        // Normal usage
        ConfigurationManager config = ConfigurationManager.INSTANCE;
        System.out.println("Config: " + config);
        
        // ATTACK 1: Reflection
        System.out.println("\n--- Reflection Attack ---");
        try {
            var constructor = ConfigurationManager.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            ConfigurationManager hacked = constructor.newInstance();
            System.out.println("Created second instance: " + hacked);
            System.out.println("Same instance? " + (config == hacked));
            System.out.println("SINGLETON BROKEN!");
        } catch (Exception e) {
            System.out.println("Reflection blocked: " + e.getMessage());
        }
        
        // Note: Serialization attack would require implementing Serializable
        // and demonstrating that deserialization creates a new instance
        
        System.out.println("\n--- Fix: Use enum singleton ---");
        System.out.println("// public enum ConfigManager { INSTANCE; ... }");
    }
}
