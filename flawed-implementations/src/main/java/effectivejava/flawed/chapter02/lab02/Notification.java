package effectivejava.flawed.chapter02.lab02;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * FLAWED IMPLEMENTATION - Demonstrates JavaBeans pattern problems
 * 
 * This notification class uses setters for construction, which leads to:
 * - Objects in inconsistent state during construction
 * - No immutability (object can be modified after creation)
 * - Thread-safety issues (mutable shared state)
 * - Unclear which fields are required vs optional
 * 
 * Study this code and identify all the problems before refactoring with Builder pattern.
 */
public class Notification {

    // Which of these are required? Optional? There's no way to tell!
    private String recipient;
    private Channel channel;
    private String message;
    private String subject;
    private Priority priority;
    private Instant scheduledTime;
    private int retryCount;
    private int retryDelaySeconds;
    private boolean trackOpens;
    private boolean trackClicks;
    private String templateId;
    private Map<String, Object> templateVariables;
    private List<String> tags;
    private String callbackUrl;
    private Instant expiresAt;

    // Default no-arg constructor - allows empty, invalid objects
    public Notification() {
        // Object is now in INVALID state - no recipient, no message!
    }

    // Setters - allow modification at any time, break immutability
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
        // No validation! Negative retries? 1000 retries?
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
        // Storing mutable reference - caller can modify it later!
        this.templateVariables = templateVariables;
    }

    public void setTags(List<String> tags) {
        // Same problem - storing mutable reference
        this.tags = tags;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    // Getters
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

    /**
     * Validate the notification - but this is called SEPARATELY from construction!
     * Nothing forces the caller to validate before sending.
     */
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
}
