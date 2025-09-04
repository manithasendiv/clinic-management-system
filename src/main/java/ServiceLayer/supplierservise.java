package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.supplier;

public class supplierservise {
    private DatabaseConnection singleConnection;

    public supplierservise() {
        singleConnection = DatabaseConnection.getSingleInstance();
    }

    // Add supplier
    public boolean addsupplier(supplier supplier) {
        try {
            String query = "INSERT INTO SupplierDetails (SupplierName, Contact, Products, CreditPeriod, BankDetails) " +
                    "VALUES('" + supplier.getSupplierName() + "','" + supplier.getContact() + "','" +
                    supplier.getProducts() + "'," + supplier.getCreditPeriod() + ",'" + supplier.getBankDetails() + "')";
            return singleConnection.ExecuteSQL(query);
        } catch (Exception e) {
            System.out.println("Error in adding supplier " + e.getMessage());
            return false;
        }
    }

    // Update supplier
    public boolean updatesupplier(supplier supplier) {
        try {
            String query = "UPDATE SupplierDetails SET SupplierName = '" + supplier.getSupplierName() +
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
