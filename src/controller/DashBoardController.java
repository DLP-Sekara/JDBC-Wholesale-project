package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class DashBoardController {
    public AnchorPane lblDashBoard;

    public void openOrderPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/MakeOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblDashBoard.getScene().getWindow();
        window.setTitle("Place a Customer Order");
        window.setMaximized(true);
        window.setScene(new Scene(load));
    }

    public void openManagePage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageOrderForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblDashBoard.getScene().getWindow();
        window.setTitle("Manage customer order");
        window.setMaximized(true);
        window.setScene(new Scene(load));
    }

    public void openManageItemPage(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageItemsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) lblDashBoard.getScene().getWindow();
        window.setTitle("Manage customer order");
        window.setMaximized(true);
        window.setScene(new Scene(load));
    }
}
