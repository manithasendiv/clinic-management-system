package Controllers;

import Models.Supplier;
import ServiceLayer.Supplierservise;

public class SupplierController {
    Supplier objSupplier;
    Supplierservise ObjSupplierService;

    public SupplierController() {
        ObjSupplierService = new Supplierservise();
    }

    // Create Supplier object
    public Supplier addSupplier(int SupplierID, String SupplierName, String Contact, String Products, int CreditPeriod, String BankDetails) {
        objSupplier = new Supplier(SupplierID, SupplierName, Contact, Products, CreditPeriod, BankDetails);
        return objSupplier;
    }

    // Add supplier to database
    public boolean addSupplierToDataBase() {
        return ObjSupplierService.addsupplier(objSupplier);
    }

    // Update supplier details
    public boolean updateSupplier(Supplier objSupplier) {
        return ObjSupplierService.updatesupplier(objSupplier);
    }

    // Delete supplier
    public boolean deleteSupplier(int SupplierID) {
        return ObjSupplierService.updatesupplier(objSupplier);
    }
}
