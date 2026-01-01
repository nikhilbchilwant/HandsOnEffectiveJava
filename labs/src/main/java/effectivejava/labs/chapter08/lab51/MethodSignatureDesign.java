package effectivejava.labs.chapter08.lab51;

import java.util.*;

/**
 * ============================================================================
 * LAB 51: Design Method Signatures Carefully (Item 51)
 * ============================================================================
 * Chapter 8, pp. 236-238
 * 
 * SCENARIO:
 * API has poor method signatures: too many parameters, confusing types.
 * 
 * YOUR TASK:
 * TODO: Apply the guidelines for clean method signatures
 * ============================================================================
 */
public class MethodSignatureDesign {

    // =========================================================================
    // BAD: Too many parameters of same type
    // =========================================================================

    // Easy to mix up arguments!
    void createUserBad(String firstName, String lastName, String email,
                       String phone, String address, String city,
                       String state, String zip) {
        // Which string is which?
    }

    // =========================================================================
    // GOOD: Use helper class/builder
    // =========================================================================

    static class UserRequest {
        private final String firstName;
        private final String lastName;
        private final String email;
        // ... etc

        private UserRequest(Builder b) {
            this.firstName = b.firstName;
            this.lastName = b.lastName;
            this.email = b.email;
        }

        static class Builder {
            private String firstName;
            private String lastName;
            private String email;

            public Builder firstName(String n) { firstName = n; return this; }
            public Builder lastName(String n) { lastName = n; return this; }
            public Builder email(String e) { email = e; return this; }
            public UserRequest build() { return new UserRequest(this); }
        }
    }

    void createUserGood(UserRequest request) {
        // Clear and can't mix up parameters
    }

    // =========================================================================
    // Avoid boolean parameters - use enums instead
    // =========================================================================

    // BAD: What does 'true' mean?
    void processBad(String data, boolean fast) { }
    // processBad(data, true);  // What's true?

    // GOOD: Self-documenting
    enum ProcessingMode { FAST, THOROUGH }
    void processGood(String data, ProcessingMode mode) { }
    // processGood(data, ProcessingMode.FAST);  // Clear!

    // =========================================================================
    // Guidelines summary
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("=== Method Signature Design ===\n");

        System.out.println("1. Choose names carefully");
        System.out.println("   - Consistent with other names");
        System.out.println("   - Readable and obvious");

        System.out.println("\n2. Don't go overboard with convenience methods");
        System.out.println("   - Every method must earn its place");

        System.out.println("\n3. Avoid long parameter lists");
        System.out.println("   - Four or fewer");
        System.out.println("   - Break up methods");
        System.out.println("   - Use helper classes");
        System.out.println("   - Use builder pattern");

        System.out.println("\n4. Prefer interfaces over classes for param types");
        System.out.println("   - Map not HashMap");

        System.out.println("\n5. Prefer enums to boolean params");
        System.out.println("   - process(Mode.FAST) not process(true)");
    }
}
