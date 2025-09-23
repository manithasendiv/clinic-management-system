package Models;




public class Supplier {
    private int SupplierID;
    private String SupplierName;
    private String Contact;
    private String Products;
    private int CreditPeriod;
    private String BankDetails;

    // Constructor
    public Supplier(int SupplierID, String SupplierName, String Contact, String Products, int CreditPeriod, String BankDetails) {
        this.SupplierID = SupplierID;
        this.SupplierName = SupplierName;
        this.Contact = Contact;
        this.Products = Products;
        this.CreditPeriod = CreditPeriod;
        this.BankDetails = BankDetails;
    }

    // Getters and setters
    public int getSupplierID() { return SupplierID; }


    public String getSupplierName() { return SupplierName; }


    public String getContact() { return Contact; }


    public String getProducts() { return Products; }


    public int getCreditPeriod() { return CreditPeriod; }


    public String getBankDetails() { return BankDetails; }

}
