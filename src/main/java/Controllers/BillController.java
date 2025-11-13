package Controllers;

import Models.Bill;
import ServiceLayer.BillService;

import java.util.List;

public class BillController {
    private Bill objBill;
    private BillService objBillService;

    public BillController() {
        objBillService = new BillService();
    }

    public Bill addBill(int patientId, String service, String pharmacyItems, double total, double discount) {
        objBill = new Bill(discount, total, pharmacyItems, service, patientId);
        return objBill;
    }

    public boolean addBillToDatabase() {
        return objBillService.addBill(objBill);
    }

    public boolean removeBill(int billId) {
        return objBillService.removeBill(billId);
    }

    public List<Bill> getAllBills() {
        return objBillService.getAllBills();
    }

    public boolean updateBill(int billId, Bill updatedBill) {
        return objBillService.updateBill(billId, updatedBill);
    }
}
