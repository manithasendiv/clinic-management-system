package DatabaseLayer;

import Models.PharmacyInventory;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class PharmacyInventoryDAO {
    public boolean addItem(PharmacyInventory item) {
        String sql = "INSERT INTO pharmacy_inventory (ItemName, Quantity, Stock, ManufactureDate, ExpiredDate, SalePrice, Discount, SupplierID, BoughtDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getQuantity());
            stmt.setInt(3, item.getStock());
            stmt.setDate(4, new java.sql.Date(item.getManufactureDate().getTime()));
            stmt.setDate(5, new java.sql.Date(item.getExpiredDate().getTime()));
            stmt.setDouble(6, item.getSalePrice());
            stmt.setDouble(7, item.getDiscount());
            stmt.setInt(8, item.getSupplierID());
            stmt.setDate(9, new java.sql.Date(item.getBoughtDate().getTime()));

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
