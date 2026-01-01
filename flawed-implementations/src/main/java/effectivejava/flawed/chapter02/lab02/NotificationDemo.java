package effectivejava.flawed.chapter02.lab02;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FLAWED IMPLEMENTATION - Demonstrates problems with JavaBeans pattern
 * 
 * This demo shows the various issues that arise from using setters for construction.
 */
public class NotificationDemo {

    public static void main(String[] args) {
        // PROBLEM 1: Inconsistent state during construction
        // Object exists but is incomplete/invalid
        Notification n1 = new Notification();
        // What happens if we try to send this now?
        System.out.println("Is valid before setting fields? " + n1.isValid()); // false
        
        n1.setRecipient("user@example.com");
        // Still incomplete!
        System.out.println("Is valid after recipient? " + n1.isValid()); // false
        
        n1.setChannel(Channel.EMAIL);
        n1.setMessage("Hello!");
        System.out.println("Is valid after all required? " + n1.isValid()); // true
        // But nothing ENFORCED this order or completeness!

        // PROBLEM 2: Object can be modified after "construction"
        Notification n2 = createValidNotification();
        System.out.println("\nOriginal: " + n2);
        
        // Later, someone modifies it (intentionally or accidentally)
        n2.setRecipient("hacker@evil.com");
        n2.setMessage("You've been pwned!");
        System.out.println("Modified: " + n2);
        // This is especially problematic if shared across threads!

        // PROBLEM 3: No validation during construction
        Notification n3 = new Notification();
        n3.setRecipient("user@example.com");
        n3.setChannel(Channel.EMAIL);
        n3.setMessage("Test");
        n3.setRetryCount(-5);  // Negative retries? Allowed!
        n3.setPriority(null);  // No priority? Allowed!
        n3.setScheduledTime(Instant.now().minus(1, ChronoUnit.DAYS)); // Past date? Allowed!
        System.out.println("\nInvalid config created without error: " + n3);

        // PROBLEM 4: Mutable references are stored directly
        Map<String, Object> vars = new HashMap<>();
        vars.put("name", "Alice");
        
        Notification n4 = new Notification();
        n4.setRecipient("user@example.com");
        n4.setChannel(Channel.EMAIL);
        n4.setMessage("Hello {{name}}!");
        n4.setTemplateVariables(vars);
        
        System.out.println("\nBefore external modification: " + n4.getTemplateVariables());
        
        // External code modifies the map!
        vars.put("name", "INJECTED_VALUE");
        vars.put("malicious", "<script>alert('xss')</script>");
        
        System.out.println("After external modification: " + n4.getTemplateVariables());
        // The notification object was corrupted through the reference!

        // PROBLEM 5: Thread-safety nightmare
        Notification shared = createValidNotification();
        // If this is shared across threads and modified, chaos ensues
        // Race conditions, visibility issues, torn reads...

        // PROBLEM 6: Unclear required vs optional
        // Looking at the class, can you tell which fields MUST be set?
        // You have to read isValid() or documentation to find out.
    }

    private static Notification createValidNotification() {
        Notification n = new Notification();
        n.setRecipient("customer@example.com");
        n.setChannel(Channel.EMAIL);
        n.setSubject("Order Confirmation");
        n.setMessage("Your order #12345 has been confirmed.");
        n.setPriority(Priority.NORMAL);
        n.setRetryCount(3);
        n.setRetryDelaySeconds(60);
        n.setTags(List.of("transactional", "order"));
        return n;
    }
}
