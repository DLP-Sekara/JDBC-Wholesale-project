package controller;

import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ClearCustomerFormController {
    public TextField txtCustomerId;
    public AnchorPane clearCustomerPage;

    public void cancelDeleteCustomer(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) clearCustomerPage.getScene().getWindow();
        window.setTitle("WHOLESALE");
        // window.setMaximized(true);
        window.setScene(new Scene(load));
    }

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (deleteCustomerOnAction(txtCustomerId.getText())){
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
        }else{
            new Alert(Alert.AlertType.WARNING, "Try Again").show();
        }
    }

    private boolean deleteCustomerOnAction(String id) throws SQLException, ClassNotFoundException {
        if(DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM Customer WHERE CustomerID='"+id+"'").executeUpdate()>0){
            return true;
        }else{
            return false;
        }
    }
}
