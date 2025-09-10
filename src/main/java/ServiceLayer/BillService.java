package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Bill;

public class BillService {
    private DatabaseConnection singleConnection;

    public BillService(){
        singleConnection = DatabaseConnection.getSingleInstance();
    }


    public boolean addBill(Bill bill){
        try{
            String query = "INSERT INTO bill_details(patientId, service , pharmacyItems , total , discount)VALUES('"
                    + bill.getPatientId() +"','"
                    + bill.getService() +"','"
                    + bill.getPharmacyItems() +"','"
                    + bill.getTotal() +"','"
                    + bill.getDiscount() +"')";
            return singleConnection.ExecuteSQL(query);
        }catch (Exception e) {
            System.out.println("Error in adding bill: " + e.getMessage());
            return false;
        }
    }

    public boolean removeBill(int billId){
        try{
            String query = "DELETE FROM bill_details WHERE billId = '" +billId+"'";
            return singleConnection.ExecuteSQL(query);
        }catch (Exception e){
            System.out.println("error in removing bill: " + e.getMessage());
            return false;
        }
    }



}
