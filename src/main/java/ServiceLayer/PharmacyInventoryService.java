package ServiceLayer;

import DatabaseLayer.PharmacyInventoryDAO;
import Models.PharmacyInventory;
import java.util.List;

public class PharmacyInventoryService {
    private PharmacyInventoryDAO dao = new PharmacyInventoryDAO();

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
