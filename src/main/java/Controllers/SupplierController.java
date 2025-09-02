package controller;

import Models.Supplier;
import service.SupplierService;
import java.util.List;

public class SupplierController {
    private SupplierService supplierService = new SupplierService();

    public void addSupplier(int id, String name, String contact, String products, int creditPeriod, String bankDetails) {
        Supplier supplier = new Supplier(id, name, contact, products, creditPeriod, bankDetails);
        supplierService.addSupplier(supplier);
    }

    public boolean deleteSupplier(int id) {
        return supplierService.deleteSupplier(id);
    }

    public boolean updateSupplier(int id, String name, String contact, String products, int creditPeriod, String bankDetails) {
        Supplier supplier = new Supplier(id, name, contact, products, creditPeriod, bankDetails);
        return supplierService.updateSupplier(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
}
