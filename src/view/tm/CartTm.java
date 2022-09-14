package view.tm;

public class CartTm {
    private String itemCode;
    private String description;
    private double unitPrice;
    private int quantity;
    private double discount;
    private double amount;

    public CartTm() {
    }

    public CartTm(String itemCode, String description, double unitPrice, int quantity, double discount, double amount) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discount = discount;
        this.amount = amount;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
        return "CartTm{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", amount=" + amount +
                '}';
    }
}
