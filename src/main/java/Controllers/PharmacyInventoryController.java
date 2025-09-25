package Controllers;

import Models.PharmacyInventory;
import ServiceLayer.PharmacyInventoryService;

import java.util.List;

public class PharmacyInventoryController {
    private PharmacyInventoryService dao = new PharmacyInventoryService();

    public boolean addInventoryItem(PharmacyInventory item) {
        return dao.addItem(item);
    }

    public List<PharmacyInventory> getAllItems() {
        return dao.getAllItems();
    }

    public boolean updateInventoryItem(PharmacyInventory item) {
        return dao.updateItem(item);
    }

    public boolean deleteInventoryItem(int id) {
        return dao.deleteItem(id);
    }
}
