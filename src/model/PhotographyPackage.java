package model;

import interfaces.Trackable;

/**
 * PhotographyPackage is an OPTIONAL service.
 * Demonstrates: INTERFACE IMPLEMENTATION (Trackable).
 */
public class PhotographyPackage implements Trackable {
    private String packageId;
    private String packageName;
    private String description;
    private String photographerName;
    private int hours;
    private double price;
    private String contactInfo;
    private boolean available;

    public PhotographyPackage(String packageId, String packageName, String description,
                               String photographerName, int hours, double price,
                               String contactInfo, boolean available) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.description = description;
        this.photographerName = photographerName;
        this.hours = hours;
        this.price = price;
        this.contactInfo = contactInfo;
        this.available = available;
    }

    public String getPackageId() { return packageId; }
    public String getPackageName() { return packageName; }
    public String getDescription() { return description; }
    public String getPhotographerName() { return photographerName; }
    public int getHours() { return hours; }
    public double getPrice() { return price; }

    @Override
    public boolean isAvailable() { return available; }

    @Override
    public String getContactInfo() { return contactInfo; }

    @Override
    public String toString() {
        return packageName + " (" + hours + " Hours) - \u09F3" + price + " | " + photographerName;
    }
}
