package Models;

import java.util.Date;

public class PharmacyInventory {
    private int itemID;
    private String itemName;
    private int quantity;
    private int stock;
    private Date manufactureDate;
    private Date expiredDate;
    private double salePrice;
    private double discount;
    private int supplierID;
    private Date boughtDate;

    // Getters & Setters
    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Date getManufactureDate() { return manufactureDate; }
    public void setManufactureDate(Date manufactureDate) { this.manufactureDate = manufactureDate; }

    public Date getExpiredDate() { return expiredDate; }
    public void setExpiredDate(Date expiredDate) { this.expiredDate = expiredDate; }

    public double getSalePrice() { return salePrice; }
    public void setSalePrice(double salePrice) { this.salePrice = salePrice; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public int getSupplierID() { return supplierID; }
    public void setSupplierID(int supplierID) { this.supplierID = supplierID; }

    public Date getBoughtDate() { return boughtDate; }
    public void setBoughtDate(Date boughtDate) { this.boughtDate = boughtDate; }
}
