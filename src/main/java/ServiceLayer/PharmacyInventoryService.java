package ServiceLayer;

import DatabaseLayer.PharmacyInventoryDAO;
import Models.PharmacyInventory;

public class PharmacyInventoryService {
    private PharmacyInventoryDAO dao = new PharmacyInventoryDAO();

    public boolean addInventoryItem(PharmacyInventory item) {
        return dao.addItem(item);
    }
}
