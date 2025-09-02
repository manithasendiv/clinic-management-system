package service;

import dao.SupplierDAO;
import Models.Supplier;
import java.util.List;

public class SupplierService {
    private SupplierDAO supplierDAO = new SupplierDAO();

    public void addSupplier(Supplier supplier) {
        supplierDAO.addSupplier(supplier);
    }

    public boolean deleteSupplier(int supplierID) {
        return supplierDAO.deleteSupplier(supplierID);
    }

    public boolean updateSupplier(Supplier supplier) {
        return supplierDAO.updateSupplier(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDAO.getAllSuppliers();
    }
}
