package Models;

public class Bill {
    private int billId;
    private int patientId;
    private String service;
    private String pharmacyItems;
    private double total;
    private double discount;

    public Bill(int billId, int patientId, String service,String pharmacyItems,double total,double discount){
        this.billId =billId;
        this.patientId= patientId;
        this.service= service;
        this.pharmacyItems =pharmacyItems;
        this.total =total;
        this.discount =discount;
    }


    public int getbillId() {return billId;}
    public int getPatientId(){return patientId;}
    public String getService() {return service;}
    public String getPharmacyItems() {return pharmacyItems;}
    public double getTotal() {return total;}
    public double getDiscount() {return discount;}
}