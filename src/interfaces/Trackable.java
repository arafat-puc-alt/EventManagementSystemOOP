package interfaces;

/**
 * Trackable is implemented by any service provider whose availability
 * and contact details need to be tracked and displayed in the GUI.
 * Demonstrates: INTERFACE + INTERFACE POLYMORPHISM.
 */
public interface Trackable {
    boolean isAvailable();
    String getContactInfo();
}
