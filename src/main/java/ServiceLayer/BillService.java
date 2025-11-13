package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Bill;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillService {
    private DatabaseConnection singleConnection;

    public BillService() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    public boolean addBill(Bill bill) {
        try {
            String query = "INSERT INTO bill_details(patientId, service, pharmacyItems, total, discount) VALUES ('"
                    + bill.getPatientId() + "','"
                    + bill.getService() + "','"
                    + bill.getPharmacyItems() + "','"
                    + bill.getTotal() + "','"
                    + bill.getDiscount() + "')";
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in adding bill: " + e.getMessage());
            return false;
        }
    }

    public boolean removeBill(int billId) {
        try {
            String query = "DELETE FROM bill_details WHERE billId = '" + billId + "'";
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in removing bill: " + e.getMessage());
            return false;
        }
    }

    // New method: update a bill
    public boolean updateBill(int billId, Bill bill) {
        try {
            String query = "UPDATE bill_details SET "
                    + "patientId = '" + bill.getPatientId() + "', "
                    + "service = '" + bill.getService() + "', "
                    + "pharmacyItems = '" + bill.getPharmacyItems() + "', "
                    + "total = '" + bill.getTotal() + "', "
                    + "discount = '" + bill.getDiscount() + "' "
                    + "WHERE billId = '" + billId + "'";
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in updating bill: " + e.getMessage());
            return false;
        }
    }

    // New method: get all bills for view table
    public List<Bill> getAllBills() {
        List<Bill> billList = new ArrayList<>();
        try {
            String query = "SELECT * FROM bill_details";
            ResultSet rs = singleConnection.getConnection().createStatement().executeQuery(query);
            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getDouble("discount"),
                        rs.getDouble("total"),
                        rs.getString("pharmacyItems"),
                        rs.getString("service"),
                        rs.getInt("patientId")
                );
                bill.setBillId(rs.getInt("billId")); // Make sure Bill class has setter for billId
                billList.add(bill);
            }
        } catch (Exception e) {
            System.out.println("Error in getting bills: " + e.getMessage());
        }
        return billList;
    }
}
