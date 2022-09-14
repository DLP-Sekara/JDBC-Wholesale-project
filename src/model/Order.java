package model;

import java.util.ArrayList;

public class Order {
    private String orderID;
    private String date;
    private String CustomerId;

    public Order(String string) {
    }

    public Order(String orderID, String date, String customerId) {
        this.orderID = orderID;
        this.date = date;
        CustomerId = customerId;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", date='" + date + '\'' +
                ", CustomerId='" + CustomerId + '\'' +
                '}';
    }
}
