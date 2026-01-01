package effectivejava.labs.chapter02.lab02;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 * LAB 02: Builder Pattern for Many Parameters (Item 2)
 * ============================================================================
 * 
 * SCENARIO:
 * You're developing a Notification Service that sends multi-channel notifications
 * (email, SMS, push, Slack). Each notification has many optional settings.
 * The current implementation uses the JavaBeans pattern (setters), which:
 * - Allows objects in inconsistent state during construction
 * - Prevents immutability
 * - Has thread-safety issues
 * 
 * YOUR TASK:
 * Refactor this class to use the Builder pattern.
 * 
 * TODO #1: Create a nested static Builder class
 * TODO #2: Builder constructor takes REQUIRED parameters (recipient, channel)
 * TODO #3: Builder has fluent setters for optional parameters (return this)
 * TODO #4: build() method validates and creates immutable Notification
 * TODO #5: Make Notification constructor private, taking only Builder
 * TODO #6: Make all fields final, remove all setters
 * TODO #7: Add validation in build():
 *          - message cannot be blank
 *          - scheduledTime must be in future if set
 * 
 * VALIDATION:
 * - Run NotificationDemo.main() before and after
 * - Before: Can create invalid notifications, modify after creation
 * - After: Compile-time safety, immutable, validated
 * 
 * REFLECTION:
 * - When is a Builder overkill? (e.g., Point(x, y)?)
 * - How does this compare to Lombok's @Builder?
 * ============================================================================
 */
public class Notification {

    // =========================================================================
    // FIXME: These fields should be final for immutability!
    // =========================================================================
    
    private String recipient;        // TODO: Make final
    private Channel channel;         // TODO: Make final
    private String message;          // TODO: Make final
    private String subject;
    private Priority priority;
    private Instant scheduledTime;
    private int retryCount;
    private int retryDelaySeconds;
    private boolean trackOpens;
    private boolean trackClicks;
    private String templateId;
    private Map<String, Object> templateVariables;  // TODO: Defensive copy!
    private List<String> tags;                       // TODO: Defensive copy!
    private String callbackUrl;
    private Instant expiresAt;

    // =========================================================================
    // FIXME: This constructor allows empty, invalid objects!
    // =========================================================================
    
    public Notification() {
        // Object is now in INVALID state - no recipient, no message!
    }

    // =========================================================================
    // FIXME: These setters break immutability and allow modification!
    // TODO: Remove all setters after implementing Builder
    // =========================================================================
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setScheduledTime(Instant scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public void setRetryCount(int retryCount) {
        // FIXME: No validation! Negative retries? 1000 retries?
        this.retryCount = retryCount;
    }

    public void setRetryDelaySeconds(int retryDelaySeconds) {
        this.retryDelaySeconds = retryDelaySeconds;
    }

    public void setTrackOpens(boolean trackOpens) {
        this.trackOpens = trackOpens;
    }

    public void setTrackClicks(boolean trackClicks) {
        this.trackClicks = trackClicks;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setTemplateVariables(Map<String, Object> templateVariables) {
        // FIXME: Storing mutable reference - caller can modify it later!
        this.templateVariables = templateVariables;
    }

    public void setTags(List<String> tags) {
        // FIXME: Same problem - storing mutable reference
        this.tags = tags;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    // =========================================================================
    // Getters (keep these, but update to return defensive copies where needed)
    // =========================================================================
    
    public String getRecipient() { return recipient; }
    public Channel getChannel() { return channel; }
    public String getMessage() { return message; }
    public String getSubject() { return subject; }
    public Priority getPriority() { return priority; }
    public Instant getScheduledTime() { return scheduledTime; }
    public int getRetryCount() { return retryCount; }
    public int getRetryDelaySeconds() { return retryDelaySeconds; }
    public boolean isTrackOpens() { return trackOpens; }
    public boolean isTrackClicks() { return trackClicks; }
    public String getTemplateId() { return templateId; }
    public Map<String, Object> getTemplateVariables() { return templateVariables; }
    public List<String> getTags() { return tags; }
    public String getCallbackUrl() { return callbackUrl; }
    public Instant getExpiresAt() { return expiresAt; }

    public boolean isValid() {
        return recipient != null && !recipient.isBlank()
                && channel != null
                && message != null && !message.isBlank();
    }

    @Override
    public String toString() {
        return String.format("Notification{to='%s', channel=%s, message='%s', priority=%s}",
                recipient, channel, 
                message != null && message.length() > 30 ? message.substring(0, 30) + "..." : message,
                priority);
    }

    // =========================================================================
    // TODO: Add your Builder class here
    // =========================================================================
    
    // Example structure:
    //
    // public static class Builder {
    //     // Required parameters
    //     private final String recipient;
    //     private final Channel channel;
    //     
    //     // Optional parameters - initialized to default values
    //     private String message = "";
    //     private Priority priority = Priority.NORMAL;
    //     // ... more fields
    //     
    //     public Builder(String recipient, Channel channel) {
    //         this.recipient = Objects.requireNonNull(recipient);
    //         this.channel = Objects.requireNonNull(channel);
    //     }
    //     
    //     public Builder message(String message) {
    //         this.message = message;
    //         return this;
    //     }
    //     
    //     // ... more fluent setters
    //     
    //     public Notification build() {
    //         // Validate
    //         if (message == null || message.isBlank()) {
    //             throw new IllegalStateException("Message is required");
    //         }
    //         return new Notification(this);
    //     }
    // }
    //
    // // Private constructor taking builder
    // private Notification(Builder builder) {
    //     this.recipient = builder.recipient;
    //     this.channel = builder.channel;
    //     // ... copy all fields
    // }
    //
    // // Convenience static factory
    // public static Builder builder(String recipient, Channel channel) {
    //     return new Builder(recipient, channel);
    // }
}
