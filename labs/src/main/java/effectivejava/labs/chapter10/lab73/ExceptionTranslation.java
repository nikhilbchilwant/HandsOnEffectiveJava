package effectivejava.labs.chapter10.lab73;

/**
 * ============================================================================
 * LAB 73: Throw Exceptions Appropriate to the Abstraction (Item 73)
 * ============================================================================
 * Chapter 10, pp. 302-304
 * 
 * SCENARIO:
 * Low-level exceptions leak through high-level abstractions.
 * Use exception translation to preserve abstraction boundaries.
 * 
 * YOUR TASK:
 * TODO: Implement exception translation (and chaining)
 * ============================================================================
 */
public class ExceptionTranslation {

    // =========================================================================
    // BAD: Low-level exception leaking through
    // =========================================================================

    static class UserRepositoryBad {
        public String getUserById(String id) throws java.sql.SQLException {
            // Implementation detail (SQL) leaks to API!
            throw new java.sql.SQLException("Connection failed");
        }
    }
    // Caller now depends on java.sql even though it's an abstraction!

    // =========================================================================
    // GOOD: Exception translation
    // =========================================================================

    static class UserNotFoundException extends Exception {
        private final String userId;
        
        UserNotFoundException(String userId, Throwable cause) {
            super("User not found: " + userId, cause);
            this.userId = userId;
        }
        
        public String getUserId() { return userId; }
    }

    static class UserRepositoryGood {
        public String getUserById(String id) throws UserNotFoundException {
            try {
                // Could throw SQLException, HibernateException, etc.
                return queryDatabase(id);
            } catch (Exception e) {
                // Translate to abstraction-appropriate exception
                // CHAIN the original cause for debugging!
                throw new UserNotFoundException(id, e);
            }
        }
        
        private String queryDatabase(String id) throws Exception {
            throw new java.sql.SQLException("Connection failed");
        }
    }

    // =========================================================================
    // Exception chaining lets you dig into root cause
    // =========================================================================

    public static void demonstrateChaining() {
        UserRepositoryGood repo = new UserRepositoryGood();
        try {
            repo.getUserById("123");
        } catch (UserNotFoundException e) {
            System.out.println("High-level: " + e.getMessage());
            System.out.println("Root cause: " + e.getCause());
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exception Translation ===\n");

        demonstrateChaining();

        System.out.println("\n--- Pattern ---");
        System.out.println("try {");
        System.out.println("    // Low-level operation");
        System.out.println("} catch (LowLevelException e) {");
        System.out.println("    throw new HighLevelException(e);  // Chain!");
        System.out.println("}");

        System.out.println("\n--- Benefits ---");
        System.out.println("1. Preserves abstraction");
        System.out.println("2. Caller doesn't need low-level dependencies");
        System.out.println("3. Chained cause helps debugging");
    }
}
