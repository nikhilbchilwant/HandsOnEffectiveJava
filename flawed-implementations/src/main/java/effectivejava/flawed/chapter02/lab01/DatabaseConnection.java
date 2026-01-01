package effectivejava.flawed.chapter02.lab01;

/**
 * FLAWED IMPLEMENTATION - Demonstrates telescoping constructor anti-pattern
 * 
 * This class represents a database connection with various configuration options.
 * Study this code and identify the problems before refactoring.
 * 
 * ISSUES TO IDENTIFY:
 * - How many constructors are there? Is this manageable?
 * - Can you tell what each boolean/int parameter means without reading docs?
 * - What if we need to add a new optional parameter?
 * - Are there any caching opportunities being missed?
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

    // Constructor 1: Full configuration
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

    // TODO: What happens if we want to add SSL certificate path?
    // TODO: What if we need compression settings?
    // TODO: How many more constructors would we need?

    // Getters (typical boilerplate)
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

    /**
     * Simulate connecting to the database.
     */
    public void connect() {
        System.out.printf("Connecting to %s:%d/%s as %s (SSL=%b, timeout=%dms)%n",
                host, port, database, username, useSSL, connectionTimeout);
    }

    /**
     * Simulate executing a query.
     */
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
