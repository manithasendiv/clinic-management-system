package dao;

import Models.Supplier;
import java.util.*;

public class SupplierDAO {
    private List<Supplier> supplierList = new ArrayList<>();

    // Add Supplier
    public void addSupplier(Supplier supplier) {
        supplierList.add(supplier);
    }

    // Delete Supplier by ID
    public boolean deleteSupplier(int supplierID) {
        return supplierList.removeIf(s -> s.getSupplierID() == supplierID);
    }

    // Update Supplier
    public boolean updateSupplier(Supplier supplier) {
        for (int i = 0; i < supplierList.size(); i++) {
            if (supplierList.get(i).getSupplierID() == supplier.getSupplierID()) {
                supplierList.set(i, supplier);
                return true;
            }
        }
        return false;
    }

    // Get all suppliers
    public List<Supplier> getAllSuppliers() {
        return supplierList;
    }
}
