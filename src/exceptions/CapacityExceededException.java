package exceptions;

/**
 * Custom CHECKED exception thrown when a selected venue cannot
 * accommodate the number of guests for an event.
 * Demonstrates: CUSTOM CHECKED EXCEPTION HANDLING.
 */
public class CapacityExceededException extends Exception {
    public CapacityExceededException(String message) {
        super(message);
    }
}
