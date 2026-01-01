package effectivejava.flawed.chapter10.lab70;

/**
 * FLAWED: Checked exception for what is essentially a programming error.
 * 
 * The caller is forced to catch this but can't do anything useful:
 * if input is null, that's a bug in the caller's code!
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
