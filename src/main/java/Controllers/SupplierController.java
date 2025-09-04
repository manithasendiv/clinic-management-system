package Controllers;

import Models.supplier;
import ServiceLayer.supplierservise;

public class SupplierController {
    supplier ObjSupplier;
    supplierservise ObjSupplierService;

    public SupplierController() {
        ObjSupplierService = new supplierservise();
    }

    // Create Supplier object
    public supplier addSupplier(int SupplierID, String SupplierName, String Contact, String Products, int CreditPeriod, String BankDetails) {
        ObjSupplier = new supplier(SupplierID, SupplierName, Contact, Products, CreditPeriod, BankDetails);
        return ObjSupplier;
    }

    // Add supplier to database
    public boolean addSupplierToDataBase() {
        return ObjSupplierService.addsupplier(ObjSupplier);
    }

    // Update supplier details
    public boolean updateSupplier(supplier ObjSupplier) {
        return ObjSupplierService.updatesupplier(ObjSupplier);
    }

    // Delete supplier
    public boolean deleteSupplier(int SupplierID) {
        return ObjSupplierService.updatesupplier(ObjSupplier);
    }
}
