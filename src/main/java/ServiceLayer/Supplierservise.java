package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Supplier;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Supplierservise {
    private DatabaseConnection singleConnection;

    public Supplierservise() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    // Add supplier
    public boolean addsupplier(Supplier supplier) {
        try {
            String query = "INSERT INTO supplier VALUES ("
                    + supplier.getSupplierID() + ", '"
                    + supplier.getSupplierName() + "', '"
                    + supplier.getContact() + "', '"
                    + supplier.getProducts() + "', "
                    + supplier.getCreditPeriod() + ", '"
                    + supplier.getBankDetails() + "')";

            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in adding supplier " + e.getMessage());
            return false;
        }
    }

    // Update supplier
    public boolean updatesupplier(Supplier supplier) {
        try {
            String query = "UPDATE supplier SET SupplierName = '" + supplier.getSupplierName() +
                    "', Contact = '" + supplier.getContact() +
                    "', Products = '" + supplier.getProducts() +
                    "', CreditPeriod = " + supplier.getCreditPeriod() +
                    ", BankDetails = '" + supplier.getBankDetails() +
                    "' WHERE SupplierID = " + supplier.getSupplierID();
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in updating supplier " + e.getMessage());
            return false;
        }
    }

    // Delete supplier
    public boolean deleteSupplier(int id) {
        try {
            String query = "DELETE FROM supplier WHERE SupplierID = " + id;
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in deleting supplier: " + e.getMessage());
            return false;
        }
    }


    public List<Supplier> getSupplier(){
        List<Supplier> patientList = new ArrayList<>();
        try{
            String query1 = "SELECT * FROM Supplier";
            ResultSet resultSet = singleConnection.executeSelectQuery(query1);
            while(resultSet.next()){
                patientList.add(new Supplier(resultSet.getInt("SupplierID"),resultSet.getString("SupplierName"),resultSet.getString("Contact"),resultSet.getString("Products"),resultSet.getInt("CreditPeriod"),resultSet.getString("BankDetails")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return patientList;
    }




}

