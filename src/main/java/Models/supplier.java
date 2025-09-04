package Models;




public class supplier {
    private int SupplierID;
    private String SupplierName;
    private String Contact;
    private String Products;
    private int CreditPeriod;
    private String BankDetails;

    // Constructor
    public supplier(int SupplierID, String SupplierName, String Contact, String Products, int CreditPeriod, String BankDetails) {
        this.SupplierID = supplierID;
        this.SupplierName = SupplierName;
        this.Contact = Contact;
        this.Products = Products;
        this.CreditPeriod = CreditPeriod;
        this.BankDetails = BankDetails;
    }

    // Getters and setters
    public int getSupplierID() { return supplierID; }


    public String getSupplierName() { return SupplierName; }


    public String getContact() { return Contact; }


    public String getProducts() { return Products; }


    public int getCreditPeriod() { return CreditPeriod; }


    public String getBankDetails() { return BankDetails; }

}
