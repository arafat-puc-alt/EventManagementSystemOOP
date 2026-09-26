package model;

import interfaces.Trackable;

/**
 * CateringPackage is an OPTIONAL service, priced per guest.
 * Demonstrates: INTERFACE IMPLEMENTATION (Trackable).
 */
public class CateringPackage implements Trackable {
    private String cateringId;
    private String packageName;
    private String menuDescription;
    private String providerName;
    private double pricePerPerson;
    private String contactInfo;
    private boolean available;

    public CateringPackage(String cateringId, String packageName, String menuDescription,
                            String providerName, double pricePerPerson,
                            String contactInfo, boolean available) {
        this.cateringId = cateringId;
        this.packageName = packageName;
        this.menuDescription = menuDescription;
        this.providerName = providerName;
        this.pricePerPerson = pricePerPerson;
        this.contactInfo = contactInfo;
        this.available = available;
    }

    public String getCateringId() { return cateringId; }
    public String getPackageName() { return packageName; }
    public String getMenuDescription() { return menuDescription; }
    public String getProviderName() { return providerName; }
    public double getPricePerPerson() { return pricePerPerson; }

    @Override
    public boolean isAvailable() { return available; }

    @Override
    public String getContactInfo() { return contactInfo; }

    @Override
    public String toString() {
        return packageName + " - \u09F3" + pricePerPerson + "/person | " + providerName;
    }
}
