package Controllers;

import Models.Bill;
import ServiceLayer.BillService;

import java.sql.ResultSet;

public class BillController {
    public Bill objBill;
    BillService objBillService;

    public BillController(){
        objBillService= new BillService();
    }
    public void addBill(int billId,int patientId,String service,String pharmacyItems, double total, double discount){
        objBill=new Bill(billId,patientId,service,pharmacyItems,total,discount);
    }

    public boolean addBillToDatabase(){
        return objBillService.addBill(objBill);
    }

    public boolean removeBill(int billId){
        return objBillService.removeBill(billId);
    }


}
