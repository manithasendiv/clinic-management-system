package ServiceLayer;

import DatabaseLayer.DatabaseConnection;
import Models.PharmacyInventory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PharmacyInventoryService {

    // Add new item
    public boolean addItem(PharmacyInventory item) {
        String sql = "INSERT INTO pharmacy_inventory " +
                "(ItemName, Quantity, Stock, ManufactureDate, ExpiredDate, SalePrice, Discount, SupplierID, BoughtDate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getSingleInstance().getConnection();
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

    // Fetch all records
    public List<PharmacyInventory> getAllItems() {
        List<PharmacyInventory> list = new ArrayList<>();
        String sql = "SELECT * FROM pharmacy_inventory";

        try (Connection conn = DatabaseConnection.getSingleInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                PharmacyInventory item = new PharmacyInventory();
                item.setItemID(rs.getInt("ItemID"));
                item.setItemName(rs.getString("ItemName"));
                item.setQuantity(rs.getInt("Quantity"));
                item.setStock(rs.getInt("Stock"));
                item.setManufactureDate(rs.getDate("ManufactureDate"));
                item.setExpiredDate(rs.getDate("ExpiredDate"));
                item.setSalePrice(rs.getDouble("SalePrice"));
                item.setDiscount(rs.getDouble("Discount"));
                item.setSupplierID(rs.getInt("SupplierID"));
                item.setBoughtDate(rs.getDate("BoughtDate"));
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update record
    public boolean updateItem(PharmacyInventory item) {
        String sql = "UPDATE pharmacy_inventory SET ItemName=?, Quantity=?, Stock=?, ManufactureDate=?, ExpiredDate=?, " +
                "SalePrice=?, Discount=?, SupplierID=?, BoughtDate=? WHERE ItemID=?";
        try (Connection conn = DatabaseConnection.getSingleInstance().getConnection();
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
            stmt.setInt(10, item.getItemID());

            int rows = stmt.executeUpdate();
            System.out.println("Rows updated: " + rows); // Debug log
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete record
    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM pharmacy_inventory WHERE ItemID=?";
        try (Connection conn = DatabaseConnection.getSingleInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
