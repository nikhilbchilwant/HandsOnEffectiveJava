package effectivejava.labs.chapter08.lab49;

import java.util.Objects;

/**
 * ============================================================================
 * LAB 49: Check Parameters for Validity (Item 49)
 * ============================================================================
 * Chapter 8, pp. 227-231
 * 
 * SCENARIO:
 * Methods accept parameters without validation. This causes confusing errors
 * deep in the code instead of at the point of misuse.
 * 
 * YOUR TASK:
 * TODO #1: Add validation at the START of public methods
 * TODO #2: Use Objects.requireNonNull() for null checks
 * TODO #3: Document @throws for validation failures
 * ============================================================================
 */
public class Account {

    private String owner;
    private double balance;

    // =========================================================================
    // FIXME: No null check! Will fail later with confusing NPE
    // =========================================================================
    
    public Account(String owner, double initialBalance) {
        // TODO: Add validation
        // Objects.requireNonNull(owner, "owner must not be null");
        // if (initialBalance < 0) {
        //     throw new IllegalArgumentException("Initial balance must be non-negative");
        // }
        this.owner = owner;
        this.balance = initialBalance;
    }

    // =========================================================================
    // FIXME: No validation leads to invalid state
    // =========================================================================
    
    public void deposit(double amount) {
        // TODO: Validate amount > 0
        // if (amount <= 0) {
        //     throw new IllegalArgumentException("Deposit amount must be positive: " + amount);
        // }
        balance += amount;
    }

    public void withdraw(double amount) {
        // TODO: Validate amount > 0 and amount <= balance
        // if (amount <= 0) {
        //     throw new IllegalArgumentException("Withdrawal amount must be positive");
        // }
        // if (amount > balance) {
        //     throw new IllegalStateException("Insufficient funds: " + balance);
        // }
        balance -= amount;
    }

    public void transfer(Account destination, double amount) {
        // TODO: Validate destination not null
        // Objects.requireNonNull(destination, "destination must not be null");
        this.withdraw(amount);
        destination.deposit(amount);
    }

    public double getBalance() { return balance; }
    public String getOwner() { return owner; }

    public static void main(String[] args) {
        System.out.println("=== Parameter Validation Demo ===\n");

        // PROBLEM 1: Null owner
        try {
            Account acc = new Account(null, 100);
            System.out.println("Owner: " + acc.getOwner().toUpperCase());  // NPE here!
        } catch (NullPointerException e) {
            System.out.println("NPE at usage, not at construction: " + e);
        }

        // PROBLEM 2: Negative deposit
        Account acc = new Account("John", 100);
        acc.deposit(-50);  // Makes no sense!
        System.out.println("Balance after -50 deposit: " + acc.getBalance());

        // PROBLEM 3: Overdraft
        acc.withdraw(200);  // More than balance!
        System.out.println("Balance after overdraft: " + acc.getBalance());

        System.out.println("\n--- Solution ---");
        System.out.println("Validate parameters at the START of methods:");
        System.out.println("- Objects.requireNonNull() for null checks");
        System.out.println("- IllegalArgumentException for bad values");
        System.out.println("- IllegalStateException for invalid state");
    }
}
