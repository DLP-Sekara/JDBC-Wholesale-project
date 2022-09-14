package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import model.Items;
import view.tm.ItemsTm;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ManageItemsFormController {
    public AnchorPane lblManageItemsPage;
    public TextField txtItemCode;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQuantityOnHand;
    public TextField txtPackSize;
    public TextField txtDiscount;
    public TableView<ItemsTm> tblItems;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TableColumn colPackSize;
    public TableColumn colDiscount;
    int cartSelectedRowForRemove = -1;
    String tempItemCode = null;

    public void backToDashBoard(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashBoard.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblManageItemsPage.getScene().getWindow();
        window.setTitle("WHOLESALE");
        // window.setMaximized(true);
        window.setScene(new Scene(load));
    }

    public void initialize() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        tblItems.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
            //tempItemCode=txtItemCode.getText();
        });
    }

    /* private void loadItems() throws SQLException, ClassNotFoundException {

         PreparedStatement stm= DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Items");
         ResultSet rst=stm.executeQuery();
         ArrayList<ItemsTm>items=new ArrayList<>();
         while (rst.next()){
             items.add(new ItemsTm(
             rst.getString(1),
             rst.getString(2),
             rst.getString(3),
             rst.getDouble(4),
             rst.getInt(5),
             rst.getDouble(6)
             ));
         }
         setItemsToTable(items);
     }

     private void setItemsToTable(ArrayList<ItemsTm> items) {
         ObservableList<ItemsTm> obList1 = FXCollections.observableArrayList();
         items.forEach(e -> {
             obList1.add(
                     new ItemsTm(e.getItemCode(), e.getDescription(), e.getPackSize(), e.getUnitPrice(), e.getQtyOnHand(), e.getDiscount()));
         });
         tblItems.setItems(obList1);
     }
 */
    ObservableList<ItemsTm> obList = FXCollections.observableArrayList();

    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        System.out.println("saved");
        //ObservableList<ItemsTm> obList = FXCollections.observableArrayList();
        Items it = new Items(txtItemCode.getText(), txtDescription.getText(), txtPackSize.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQuantityOnHand.getText()), Double.parseDouble(txtDiscount.getText()));
        if (saveItems(it))
            new Alert(Alert.AlertType.CONFIRMATION, "Saved..").show();
        else
            new Alert(Alert.AlertType.WARNING, "Try Again..").show();


        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQuantityOnHand.getText());
        double discount = Double.parseDouble(txtDiscount.getText());

        ItemsTm tm = new ItemsTm(itemCode, description, packSize, unitPrice, qtyOnHand, discount);
        obList.add(tm);
        tblItems.setItems(obList);

    }

    private boolean saveItems(Items it) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String query = "INSERT INTO Item VALUES(?,?,?,?,?,?)";
        PreparedStatement stm = con.prepareStatement(query);
        stm.setObject(1, it.getItemCode());
        stm.setObject(2, it.getDescription());
        stm.setObject(3, it.getPackSize());
        stm.setObject(4, it.getUnitPrice());
        stm.setObject(5, it.getQtyOnHand());
        stm.setObject(6, it.getDiscount());
        return stm.executeUpdate() > 0;
    }

    public void deleteItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {


        if (cartSelectedRowForRemove == -1) {
            new Alert(Alert.AlertType.WARNING, "Please Select a row").show();
        } else {
            String tempCode = tblItems.getSelectionModel().getSelectedItem().getItemCode();
            obList.remove(cartSelectedRowForRemove);
            tblItems.refresh();

            delete(tempCode);

        }
    }

    boolean delete(String id) throws SQLException, ClassNotFoundException {
        if (DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Item WHERE ItemCode='" + id + "'").executeUpdate() > 0) {
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
            return true;
        } else {
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
            return false;
        }
    }

    /*public void LoadAllItemsOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        setItemsToTable(getAllItems());
    }

    private void setItemsToTable(ArrayList<Items>items) {
        ObservableList<ItemsTm>obList=FXCollections.observableArrayList();
        items.forEach(e->{
            obList.add(new ItemsTm(e.getItemCode(),e.getDescription(),e.getPackSize(),e.getUnitPrice(),e.getQtyOnHand(),e.getDiscount()));
        });
        tblItems.setItems(obList);
    }

    private ArrayList<Items> getAllItems() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer");
        ResultSet rst = stm.executeQuery();
        //ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
        ArrayList<Items> items = new ArrayList<>();

        while (rst.next()) {
            items.add(new Items(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getDouble(6)
            ));
        }
        return items;
    }*/
}
