package Models;

public class Bill {
    private int billId;
    private int patientId;
    private String service;
    private String pharmacyItems;
    private double total;
    private double discount;

    public Bill(int billId, int patientId, String service, String pharmacyItems, double total, double discount) {
        this.billId = billId;
        this.patientId = patientId;
        this.service = service;
        this.pharmacyItems = pharmacyItems;
        this.total = total;
        this.discount = discount;
    }

    public Bill(double discount, double total, String pharmacyItems, String service, int patientId) {
        this.discount = discount;
        this.total = total;
        this.pharmacyItems = pharmacyItems;
        this.service = service;
        this.patientId = patientId;
    }

    public int getBillId() { return billId; }
    public int getPatientId() { return patientId; }
    public String getService() { return service; }
    public String getPharmacyItems() { return pharmacyItems; }
    public double getTotal() { return total; }
    public double getDiscount() { return discount; }

    public void setBillId(int billId) { this.billId = billId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setService(String service) { this.service = service; }
    public void setPharmacyItems(String pharmacyItems) { this.pharmacyItems = pharmacyItems; }
    public void setTotal(double total) { this.total = total; }
    public void setDiscount(double discount) { this.discount = discount; }
}
