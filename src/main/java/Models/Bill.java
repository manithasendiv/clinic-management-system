package Models;

import java.util.List;

public class Bill {
    private String billId;
    private int patientId;
    private String service;
    private List<PharmacyItem> pharmacyItems;
    private double total;
    private double discount;
    private String billPath;

    public Bill(double discount, String service, int patientId, String billId, double total,String billPath) {
        this.discount = discount;
        this.service = service;
        this.patientId = patientId;
        this.billId = billId;
        this.total = total;
        this.billPath = billPath;
    }

    public Bill(String billId, int patientId, String service, List<PharmacyItem> pharmacyItems, double total, double discount) {
        this.billId = billId;
        this.patientId = patientId;
        this.service = service;
        this.pharmacyItems = pharmacyItems;
        this.total = total;
        this.discount = discount;
    }

    public Bill(double discount, double total, List<PharmacyItem> pharmacyItems, String service, int patientId) {
        this.discount = discount;
        this.total = total;
        this.pharmacyItems = pharmacyItems;
        this.service = service;
        this.patientId = patientId;
    }
    public String getBillPath() {
        return billPath;
    }

    public void setBillPath(String billPath) {
        this.billPath = billPath;
    }
    public String getBillId() { return billId; }
    public int getPatientId() { return patientId; }
    public String getService() { return service; }
    public List<PharmacyItem> getPharmacyItems() { return pharmacyItems; }
    public double getTotal() { return total; }
    public double getDiscount() { return discount; }

    public void setBillId(String billId) { this.billId = billId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setService(String service) { this.service = service; }
    public void setPharmacyItems(List<PharmacyItem> pharmacyItems) { this.pharmacyItems = pharmacyItems; }
    public void setTotal(double total) { this.total = total; }
    public void setDiscount(double discount) { this.discount = discount; }
}
