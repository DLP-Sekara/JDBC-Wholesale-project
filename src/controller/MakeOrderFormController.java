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
import model.Customer;
import model.Items;
import model.Order;
import model.OrderDetails;
import view.tm.CartTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeOrderFormController {
    public AnchorPane lblPlaceOrderPage;
    public TextField txtCustomerPostalCode;
    public TextField txtCustomerProvince;
    public TextField txtCustomerCity;
    public TextField txtCustomerAddress;
    public TextField txtCustomerName;
    public TextField txtCustomerTitle;
    public Label lblCustomerId;
    public ComboBox<String> cmbItemCode;
    //public Button searchItemInfoOnAction;
    //public Button addToOnAction;
    public TextField txtQuantity;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtPackSize;
    public TextField txtDescription;
    public TableView<CartTm> tblItemData;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQuantity;
    public TableColumn colDiscount;
    public TableColumn colAmount;
    public TextField txtDiscount;
    public Label txtTotal2;
    public Label txtTotal1;
    public Label txtDate;
    public Label lblOrderId;
    int cartSelectedRowForRemove = -1;

    public void backToDashBoard(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoard.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblPlaceOrderPage.getScene().getWindow();
        window.setTitle("WHOLESALE");
        // window.setMaximized(true);
        window.setScene(new Scene(load));
    }

    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        System.out.println("nnnnnnnn");
        int id = Integer.parseInt(lblCustomerId.getText());
        id++;
        lblCustomerId.setText(String.valueOf(id));

        Customer c1 = new Customer(lblCustomerId.getText(), txtCustomerTitle.getText(), txtCustomerName.getText(), txtCustomerAddress.getText(),
                txtCustomerCity.getText(), txtCustomerProvince.getText(), txtCustomerPostalCode.getText());
        if (saveCustomer(c1))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();
    }

    boolean saveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        String query = "INSERT INTO Customer VALUES(?,?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);

        stm.setObject(1, c.getCustId());
        stm.setObject(2, c.getCustTitle());
        stm.setObject(3, c.getCustName());
        stm.setObject(4, c.getCustAddress());
        stm.setObject(5, c.getCustCity());
        stm.setObject(6, c.getCustProvince());
        stm.setObject(7, c.getCustPostalCode());


        return stm.executeUpdate() > 0;
    }

    public void initialize() throws SQLException, ClassNotFoundException {



        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        loadItem();
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                setItemData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }));



        tblItemData.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
            //tempItemCode=txtItemCode.getText();
        });

    }

    private void setItemData(String newValue) throws SQLException, ClassNotFoundException {
        Items i1 = getItem(newValue);
        if (i1 == null) {
            new Alert(Alert.AlertType.WARNING, "Empty Result Set");
        } else {
            txtDescription.setText(i1.getDescription());
            txtPackSize.setText(i1.getPackSize());
            txtUnitPrice.setText(String.valueOf(i1.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtDiscount.setText(String.valueOf(i1.getDiscount()));
        }
    }

    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
        txtDate.setText(f.format(date));

        /*Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();*/
    }
    private Items getItem(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item WHERE ItemCode='" + newValue + "'").executeQuery();
        if (rst.next()) {
            return new Items(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            );
        } else {
            return null;
        }
    }

    public void searchItemsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

    }

    public void loadItem() throws SQLException, ClassNotFoundException {
        System.out.println("done");
        List<String> itemId = getIds();
        cmbItemCode.getItems().addAll(itemId);
    }

    private List<String> getIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Item").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()) {
            ids.add(
                    rst.getString(1)
            );
        }
        return ids;
    }

    ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void addToCartOnAction(ActionEvent actionEvent) {
        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        double discount = Double.parseDouble(txtDiscount.getText());
        double amount = (unitPrice * quantity) - (unitPrice * quantity * discount / 100);

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        if (qtyOnHand < quantity) {
            new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
            return;
        }
        CartTm tm = new CartTm(itemCode, description, unitPrice, quantity, discount, amount);
        /*obList.add(tm);
        tblItemData.setItems(obList);*/

        int rowNumber = isExists(tm);
        if (rowNumber == -1) {// new Add
            obList.add(tm);
        } else {
            // update
            CartTm temp = obList.get(rowNumber);
            CartTm newTm = new CartTm(temp.getItemCode(), temp.getDescription(), temp.getUnitPrice(),
                    temp.getQuantity()+quantity, temp.getDiscount(), amount + temp.getAmount());

            if (qtyOnHand <= temp.getQuantity()) {
                new Alert(Alert.AlertType.WARNING, "Invalid QTY").show();
                return;
            }

            obList.remove(rowNumber);
            obList.add(newTm);
        }
        tblItemData.setItems(obList);
        calculateCost();
    }

    private int isExists(CartTm tm) {
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemCode().equals(obList.get(i).getItemCode())) {
                return i;
            }
        }
        return -1;
    }

    void calculateCost() {
        double ttl = 0;
        for (CartTm tm : obList
        ) {
            ttl += tm.getAmount();
        }
        txtTotal1.setText(ttl + " /=");
        txtTotal2.setText(ttl + " /=");
    }

    public void clearItemOnAction(ActionEvent actionEvent) {
        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            obList.remove(cartSelectedRowForRemove);
            calculateCost();
            tblItemData.refresh();
        }
    }

    public void confirmOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        loadDate();
        int tempOrder=Integer.parseInt(lblOrderId.getText());
        tempOrder++;
        lblOrderId.setText(String.valueOf(tempOrder));

        String tempId=cmbItemCode.getSelectionModel().getSelectedItem();

        Order order=new Order(lblOrderId.getText(),txtDate.getText(),lblCustomerId.getText());
        saveOrder(order);

        OrderDetails orderDetails=new OrderDetails(lblOrderId.getText(),tempId,Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQuantity.getText()),Double.parseDouble(txtDiscount.getText()));
        saveOrderDetails(orderDetails);
        /*ArrayList<OrderDetails>orders=new ArrayList<>();
        for (CartTm tempTm:obList
        ) {
            orders.add(
                    new OrderDetails(
                            "0-001",
                            tempTm.getItemCode(),
                            tempTm.getUnitPrice(),
                            tempTm.getQuantity(),
                            tempTm.getDiscount()

                    )
            );
        }
        Order order=new Order(
                "0-001",
                txtDate.getText(),
                lblCustomerId.getText(),
                orders
        );

        try {
            if (placeOrder(order)){
                new Alert(Alert.AlertType.CONFIRMATION, "Success").show();
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

    }

    private boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Orders VALUES(?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,order.getOrderID());
        stm.setObject(2,order.getDate());
        stm.setObject(3,order.getCustomerId());
        return stm.executeUpdate() > 0;
    }

    private boolean saveOrderDetails(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO OrderDetail VALUES(?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1,orderDetails.getOrderID());
        stm.setObject(2,orderDetails.getItemCode());
        stm.setObject(3,orderDetails.getUnitPrice());
        stm.setObject(4,orderDetails.getOrderQty());
        stm.setObject(5,orderDetails.getDiscount());
        return stm.executeUpdate() > 0;
    }

    public void clearCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("../view/ClearCustomerForm.fxml"));
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

   /* private boolean placeOrder(Order order) throws SQLException, ClassNotFoundException {
       PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Orders VALUES(?,?,?)");

       stm.setObject(1,order.getOrderID());
       stm.setObject(2,order.getDate());
       stm.setObject(3,order.getCustomerId());
        return stm.executeUpdate() > 0;
           // return false;
    }*/
}


