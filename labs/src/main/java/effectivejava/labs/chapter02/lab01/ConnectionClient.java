package effectivejava.labs.chapter02.lab01;

/**
 * Client code demonstrating the problems with telescoping constructors.
 * 
 * Run this BEFORE and AFTER your refactoring to see the improvement.
 */
public class ConnectionClient {

    public static void main(String[] args) {
        System.out.println("=== BEFORE REFACTORING ===");
        System.out.println("(These constructor calls are confusing!)\n");

        // PROBLEM 1: What do all these booleans and ints mean?
        DatabaseConnection conn1 = new DatabaseConnection(
                "prod-db.example.com",
                5432,
                "orders",
                "app_user",
                "secret123",
                true,   // What is this? SSL? Pooling?
                false,  // And this? Auto-commit? Lazy?
                30000,  // Timeout in seconds? Milliseconds? Retries?
                60000,  // Same question...
                true,   // Read-only? Pooled? Cached?
                10,     // Pool size? Max connections? Queue size?
                true    // Lazy? Eager? Something else?
        );
        System.out.println("Created: " + conn1);

        // PROBLEM 2: Which constructor for local development?
        DatabaseConnection localDev = new DatabaseConnection(
                "localhost",
                "test_db"
        );
        System.out.println("Local dev: " + localDev);

        // PROBLEM 3: Creating similar connections doesn't share instances
        DatabaseConnection conn2 = new DatabaseConnection("localhost", "test_db");
        DatabaseConnection conn3 = new DatabaseConnection("localhost", "test_db");
        System.out.println("Same instance? " + (conn2 == conn3));  // false

        System.out.println("\n=== AFTER REFACTORING ===");
        System.out.println("(Uncomment these after implementing static factories)\n");

        // TODO: Uncomment after implementing static factory methods:
        //
        // // Clear intent from method name!
        // DatabaseConnection local = DatabaseConnection.localDevConnection();
        // System.out.println("Local: " + local);
        //
        // // Cached instance - same object returned!
        // DatabaseConnection local2 = DatabaseConnection.localDevConnection();
        // System.out.println("Same cached instance? " + (local == local2));  // true!
        //
        // // Production connection with clear parameters
        // DatabaseConnection prod = DatabaseConnection.productionConnection(
        //         "prod-db.example.com", 5432);
        // System.out.println("Production: " + prod);
        //
        // // Pooled connection for high throughput
        // DatabaseConnection pooled = DatabaseConnection.pooledConnection(10, true);
        // System.out.println("Pooled: " + pooled);
    }
}
