package model;

public class OrderDetails {
    private String orderID;
    private String itemCode;
    private double unitPrice;
    private int orderQty;
    private double discount;

    public OrderDetails() {
    }

    public OrderDetails(String orderID, String itemCode, double unitPrice, int orderQty, double discount) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.discount = discount;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderID='" + orderID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                '}';
    }
}
