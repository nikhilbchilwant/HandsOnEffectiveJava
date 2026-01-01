package effectivejava.flawed.chapter02.lab01;

/**
 * FLAWED IMPLEMENTATION - Client code demonstrating constructor confusion
 * 
 * Study this client code to understand why telescoping constructors are problematic.
 * Notice how unclear the constructor calls are without IDE assistance.
 */
public class ConnectionClient {

    public static void main(String[] args) {
        // PROBLEM 1: What do all these booleans and ints mean?
        // Without reading docs, can you tell what each parameter does?
        
        DatabaseConnection conn1 = new DatabaseConnection(
                "prod-db.example.com",
                5432,
                "orders",
                "app_user",
                "secret123",
                true,   // What is this?
                false,  // And this?
                30000,  // Timeout in seconds? Milliseconds? Retries?
                60000,  // Same question...
                true,   // Read-only? Pooled? Cached?
                10,     // Pool size? Max connections? Queue size?
                true    // Lazy? Eager? Something else?
        );

        // PROBLEM 2: Which constructor should I use for "just local development"?
        // Do I use the 2-param version? 5-param? Something else?
        
        DatabaseConnection localDev = new DatabaseConnection(
                "localhost",
                "test_db"
        );
        // Wait, what are the default credentials? Is SSL on or off?
        // Will this work for my local Postgres without SSL?

        // PROBLEM 3: I want custom timeouts but default everything else
        // There's no constructor for that! I have to use the big one...
        
        DatabaseConnection customTimeouts = new DatabaseConnection(
                "localhost",
                5432,
                "test_db",
                "dev",
                "dev",
                false,  // No SSL for local
                true,
                5000,
                10000,
                false,
                1,
                false
        );
        // I had to specify 12 parameters when I only cared about 2!

        // PROBLEM 4: Creating similar connections doesn't share instances
        DatabaseConnection conn2 = new DatabaseConnection("localhost", "test_db");
        DatabaseConnection conn3 = new DatabaseConnection("localhost", "test_db");
        
        // These are identical configurations but separate objects
        System.out.println("Same instance? " + (conn2 == conn3));  // false
        // For read-only or dev connections, we could reuse!

        // PROBLEM 5: Can't easily get a readonly replica connection
        // There's no constructor that makes this intent clear

        // Demonstrate the connections work
        conn1.connect();
        localDev.executeQuery("SELECT * FROM users");
    }
}
