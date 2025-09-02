package Models;



public class Supplier {
    private int supplierID;
    private String supplierName;
    private String contact;
    private String products;
    private int creditPeriod;
    private String bankDetails;

    // Constructor
    public Supplier(int supplierID, String supplierName, String contact, String products, int creditPeriod, String bankDetails) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.contact = contact;
        this.products = products;
        this.creditPeriod = creditPeriod;
        this.bankDetails = bankDetails;
    }

    // Getters and setters
    public int getSupplierID() { return supplierID; }
    public void setSupplierID(int supplierID) { this.supplierID = supplierID; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getProducts() { return products; }
    public void setProducts(String products) { this.products = products; }

    public int getCreditPeriod() { return creditPeriod; }
    public void setCreditPeriod(int creditPeriod) { this.creditPeriod = creditPeriod; }

    public String getBankDetails() { return bankDetails; }
    public void setBankDetails(String bankDetails) { this.bankDetails = bankDetails; }
}
