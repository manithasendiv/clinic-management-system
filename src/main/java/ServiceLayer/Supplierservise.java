package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.Supplier;

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
}
