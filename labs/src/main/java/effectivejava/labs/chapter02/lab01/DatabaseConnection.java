package effectivejava.labs.chapter02.lab01;

/**
 * ============================================================================
 * LAB 01: Static Factory Methods vs Constructors (Item 1)
 * ============================================================================
 * 
 * SCENARIO:
 * You're building a Connection Pool Manager for a database client library.
 * The current implementation uses "telescoping constructors" - too many
 * constructor overloads with unclear parameter meanings.
 * 
 * PROBLEMS TO FIX:
 * 1. Too many constructors - hard to know which to use
 * 2. Unclear parameters - what does "true, false, 5000" mean?
 * 3. No instance caching - creates new object every time
 * 4. No meaningful names - constructors can't have descriptive names
 * 
 * YOUR TASK:
 * Refactor this class to use static factory methods instead of constructors.
 * 
 * TODO #1: Make constructor(s) private
 * TODO #2: Create these static factory methods:
 *          - localDevConnection() - for local development
 *          - productionConnection(String host, int port) - for production
 *          - pooledConnection(int poolSize, boolean lazyInit) - for pooling
 *          - fromProperties(Properties config) - from config file
 * TODO #3: Add instance caching for localDevConnection() 
 *          (return same instance each time)
 * TODO #4: (Bonus) Create a Connection interface that this could return
 * 
 * VALIDATION:
 * - Run ConnectionClient.main() before and after your changes
 * - Before: Confusing constructor calls
 * - After: Clear, self-documenting factory methods
 * 
 * REFLECTION:
 * - When would constructors still be preferable?
 * - How does this pattern affect testability?
 * ============================================================================
 */
public class DatabaseConnection {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final boolean useSSL;
    private final boolean autoReconnect;
    private final int connectionTimeout;
    private final int queryTimeout;
    private final boolean readOnly;
    private final int poolSize;
    private final boolean lazyInit;

    // =========================================================================
    // FIXME: These telescoping constructors are confusing!
    // Replace with static factory methods.
    // =========================================================================

    // Constructor 1: Full configuration (12 parameters!)
    public DatabaseConnection(String host, int port, String database, 
                              String username, String password,
                              boolean useSSL, boolean autoReconnect,
                              int connectionTimeout, int queryTimeout,
                              boolean readOnly, int poolSize, boolean lazyInit) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.useSSL = useSSL;
        this.autoReconnect = autoReconnect;
        this.connectionTimeout = connectionTimeout;
        this.queryTimeout = queryTimeout;
        this.readOnly = readOnly;
        this.poolSize = poolSize;
        this.lazyInit = lazyInit;
    }

    // Constructor 2: Without pool settings
    public DatabaseConnection(String host, int port, String database,
                              String username, String password,
                              boolean useSSL, boolean autoReconnect,
                              int connectionTimeout, int queryTimeout,
                              boolean readOnly) {
        this(host, port, database, username, password, useSSL, autoReconnect,
             connectionTimeout, queryTimeout, readOnly, 1, false);
    }

    // Constructor 3: Without read-only mode
    public DatabaseConnection(String host, int port, String database,
                              String username, String password,
                              boolean useSSL, boolean autoReconnect,
                              int connectionTimeout, int queryTimeout) {
        this(host, port, database, username, password, useSSL, autoReconnect,
             connectionTimeout, queryTimeout, false, 1, false);
    }

    // Constructor 4: With timeouts only
    public DatabaseConnection(String host, int port, String database,
                              String username, String password,
                              int connectionTimeout, int queryTimeout) {
        this(host, port, database, username, password, true, true,
             connectionTimeout, queryTimeout, false, 1, false);
    }

    // Constructor 5: Basic with credentials
    public DatabaseConnection(String host, int port, String database,
                              String username, String password) {
        this(host, port, database, username, password, true, true,
             30000, 60000, false, 1, false);
    }

    // Constructor 6: Minimal (uses defaults heavily)
    public DatabaseConnection(String host, String database) {
        this(host, 5432, database, "admin", "admin", true, true,
             30000, 60000, false, 1, false);
    }

    // =========================================================================
    // TODO: Add your static factory methods here
    // =========================================================================
    
    // Example structure (uncomment and implement):
    //
    // private static final DatabaseConnection LOCAL_DEV_INSTANCE = ...;
    //
    // public static DatabaseConnection localDevConnection() {
    //     // TODO: Return cached instance for local development
    // }
    //
    // public static DatabaseConnection productionConnection(String host, int port) {
    //     // TODO: Create production connection with sensible defaults
    // }
    //
    // public static DatabaseConnection pooledConnection(int poolSize, boolean lazyInit) {
    //     // TODO: Create pooled connection for high-throughput scenarios
    // }

    // =========================================================================
    // Existing methods (keep these)
    // =========================================================================

    public String getHost() { return host; }
    public int getPort() { return port; }
    public String getDatabase() { return database; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isUseSSL() { return useSSL; }
    public boolean isAutoReconnect() { return autoReconnect; }
    public int getConnectionTimeout() { return connectionTimeout; }
    public int getQueryTimeout() { return queryTimeout; }
    public boolean isReadOnly() { return readOnly; }
    public int getPoolSize() { return poolSize; }
    public boolean isLazyInit() { return lazyInit; }

    public void connect() {
        System.out.printf("Connecting to %s:%d/%s as %s (SSL=%b, timeout=%dms)%n",
                host, port, database, username, useSSL, connectionTimeout);
    }

    public void executeQuery(String sql) {
        System.out.printf("Executing: %s (queryTimeout=%dms, readOnly=%b)%n",
                sql, queryTimeout, readOnly);
    }

    @Override
    public String toString() {
        return String.format("DatabaseConnection{host='%s', port=%d, database='%s', " +
                        "username='%s', useSSL=%b, autoReconnect=%b, " +
                        "connectionTimeout=%d, queryTimeout=%d, readOnly=%b, " +
                        "poolSize=%d, lazyInit=%b}",
                host, port, database, username, useSSL, autoReconnect,
                connectionTimeout, queryTimeout, readOnly, poolSize, lazyInit);
    }
}
