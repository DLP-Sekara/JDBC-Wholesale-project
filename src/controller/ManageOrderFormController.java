package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Items;
import model.Order;
import model.OrderDetails;
import view.tm.CartTm;
import view.tm.OrderTm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageOrderFormController {
    public AnchorPane lblManageOrderPage;
    public Label lblOrderId;
    public ComboBox<String> cmbCustomerId;
    public TextArea txtOrderId;
    public MenuButton menubtnOrderIds;
    public ComboBox <String>cmbOrderIds;
    public TableView <OrderTm>tblOrderDetails;
    public TableColumn colItemCode;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colAmount;

    public void initialize() throws SQLException, ClassNotFoundException {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
     loadCustomerid();
     loadOrderid();
        cmbOrderIds.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }));
    }
    ObservableList<OrderTm> obList = FXCollections.observableArrayList();
    private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        OrderDetails o1 = getItem(newValue);
        if (o1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            String orderId=o1.getOrderID();
            String itemCode=o1.getItemCode();
            double unitPrice =o1.getUnitPrice();
            int qtyOnHand = o1.getOrderQty();
            double discount =o1.getDiscount();
            double amount = (unitPrice * qtyOnHand) - (unitPrice * qtyOnHand * discount / 100);

           OrderTm tempOrder=new OrderTm(orderId,itemCode,unitPrice,qtyOnHand,discount,amount);
            obList.add(tempOrder);
            tblOrderDetails.setItems(obList);
        }
    }

    private OrderDetails getItem(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM OrderDetail WHERE OrderID='" + newValue + "'").executeQuery();
        if (rst.next()) {
            return new OrderDetails(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4),
                    rst.getDouble(5)
            );
        } else {
            return null;
        }
    }

    private void loadOrderid() throws SQLException, ClassNotFoundException {
        List<String> orderid = getorderIds();
        cmbOrderIds.getItems().addAll(orderid);
    }

    private List<String> getorderIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Orders").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }
  /*  private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        Order o1 = getItem(newValue);
        if (o1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtOrderId.setText(o1.getOrderID());
        }
    }

    private Order getItem(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Orders WHERE CustomerID='" + newValue + "'").executeQuery();
        if (rst.next()) {
            return new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        } else {
            return null;
        }
    }*/
    private void loadCustomerid() throws SQLException, ClassNotFoundException {
        List<String> itemId = getIds();
        cmbCustomerId.getItems().addAll(itemId);
    }

    private List<String> getIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    public void backToDashBoard(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoard.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblManageOrderPage.getScene().getWindow();
        window.setTitle("WHOLESALE");
        // window.setMaximized(true);
        window.setScene(new Scene(load));
    }
}



