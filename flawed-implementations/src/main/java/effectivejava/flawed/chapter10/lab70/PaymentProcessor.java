package effectivejava.flawed.chapter10.lab70;

/**
 * FLAWED IMPLEMENTATION - Wrong exception design choices
 * 
 * This payment processor makes poor decisions about checked vs unchecked:
 * - Checked exceptions for programming errors (burden on caller)
 * - Unchecked exceptions for recoverable conditions (silent crashes)
 */
public class PaymentProcessor {

    /**
     * WRONG: NullPointerException is appropriate here (programming error)
     * but we're throwing a CHECKED exception, burdening the caller.
     */
    public void processPayment(String cardNumber, double amount) 
            throws InvalidInputException {  // WRONG: checked for programming error
        if (cardNumber == null) {
            throw new InvalidInputException("Card number cannot be null");
        }
        if (amount <= 0) {
            throw new InvalidInputException("Amount must be positive");
        }
        // Process...
    }

    /**
     * WRONG: Insufficient funds IS recoverable (try another card),
     * but we throw RuntimeException and caller doesn't know to handle it!
     */
    public void chargeCard(String cardNumber, double amount) {
        double available = checkBalance(cardNumber);
        if (amount > available) {
            // WRONG: This is RECOVERABLE - should be checked!
            throw new RuntimeException("Insufficient funds: " + 
                "requested " + amount + " but only " + available + " available");
        }
        // Charge...
    }

    /**
     * WRONG: Network errors ARE recoverable (retry), but no checked exception.
     */
    public void validateCard(String cardNumber) {
        if (!connectToNetwork()) {
            // WRONG: Caller should be prompted to handle network failure!
            throw new RuntimeException("Network timeout");
        }
        // Validate...
    }

    /**
     * TOO BROAD: Throws Exception (too generic).
     */
    public void refund(String transactionId, double amount) throws Exception {
        // Everything throws Exception - tells caller nothing!
        if (transactionId == null) {
            throw new Exception("Transaction ID required");
        }
        if (!transactionExists(transactionId)) {
            throw new Exception("Transaction not found");
        }
        if (amount <= 0) {
            throw new Exception("Invalid amount");
        }
        // Refund...
    }

    // Simulated methods
    private double checkBalance(String cardNumber) { return 100.0; }
    private boolean connectToNetwork() { return Math.random() > 0.1; }
    private boolean transactionExists(String id) { return true; }
}
