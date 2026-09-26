package model;

import interfaces.Trackable;

/**
 * Decoration is an OPTIONAL service.
 * Demonstrates: INTERFACE IMPLEMENTATION (Trackable).
 */
public class Decoration implements Trackable {
    private String decorationId;
    private String name;
    private String description;
    private String packageType;
    private double price;
    private String providerName;
    private String contactInfo;
    private boolean available;

    public Decoration(String decorationId, String name, String description, String packageType,
                       double price, String providerName, String contactInfo, boolean available) {
        this.decorationId = decorationId;
        this.name = name;
        this.description = description;
        this.packageType = packageType;
        this.price = price;
        this.providerName = providerName;
        this.contactInfo = contactInfo;
        this.available = available;
    }

    public String getDecorationId() { return decorationId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPackageType() { return packageType; }
    public double getPrice() { return price; }
    public String getProviderName() { return providerName; }

    @Override
    public boolean isAvailable() { return available; }

    @Override
    public String getContactInfo() { return contactInfo; }

    @Override
    public String toString() {
        return name + " (" + packageType + ") - \u09F3" + price + " | " + providerName;
    }
}
