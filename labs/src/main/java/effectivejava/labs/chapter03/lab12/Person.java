package effectivejava.labs.chapter03.lab12;

/**
 * ============================================================================
 * LAB 12: Always Override toString (Item 12)
 * ============================================================================
 * Chapter 3, pp. 55-58
 * 
 * SCENARIO:
 * Classes use default toString() which prints useless output like:
 * "PhoneNumber@163b91"
 * 
 * A good toString() makes debugging and logging much easier!
 * 
 * YOUR TASK:
 * TODO #1: Override toString() to return useful info
 * TODO #2: Document whether format is part of the contract
 * TODO #3: Provide programmatic access to the data (getters)
 * ============================================================================
 */
public class Person {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final int age;

    public Person(String firstName, String lastName, String email, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    // =========================================================================
    // FIXME: Missing toString()!
    // Default: "Person@1a2b3c" — useless!
    // =========================================================================
    
    // TODO: Add a good toString():
    //
    // /**
    //  * Returns a string representation of this person.
    //  * Format: "FirstName LastName (email), age X"
    //  * 
    //  * NOTE: The format is not part of the specification and may change.
    //  * Use getters for programmatic access.
    //  */
    // @Override
    // public String toString() {
    //     return String.format("%s %s (%s), age %d",
    //             firstName, lastName, email, age);
    // }

    public static void main(String[] args) {
        System.out.println("=== toString Demo ===\n");

        Person person = new Person("John", "Doe", "john@example.com", 30);

        // Without toString: prints useless hash
        System.out.println("person: " + person);

        System.out.println("\nExpected after fix:");
        System.out.println("person: John Doe (john@example.com), age 30");

        System.out.println("\n--- Guidelines ---");
        System.out.println("1. Include all interesting info");
        System.out.println("2. Document if format is part of contract");
        System.out.println("3. Provide getters for programmatic access");
        System.out.println("4. Don't include sensitive data (passwords!)");
    }
}
