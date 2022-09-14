package view.tm;

public class OrderTm {
    private String orderID;
    private String itemCode;
    private double unitPrice;
    private int orderQty;
    private double discount;
    private double amount;

    public OrderTm() {
    }

    public OrderTm(String orderID, String itemCode, double unitPrice, int orderQty, double discount, double amount) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.unitPrice = unitPrice;
        this.orderQty = orderQty;
        this.discount = discount;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "orderID='" + orderID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", unitPrice=" + unitPrice +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", amount=" + amount +
                '}';
    }
}
