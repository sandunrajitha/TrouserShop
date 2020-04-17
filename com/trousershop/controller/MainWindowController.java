/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import static java.awt.Color.BLUE;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Sandun
 */
public class MainWindowController implements Initializable {

    @FXML
    private Button ordersButton;
    @FXML
    private Button itemsButton;
    @FXML
    private Button customersButton;
    @FXML
    private Button suppliersButton;
    @FXML
    private Button employeesButton;
    @FXML
    private Button purchOrdersButton;
    @FXML
    private Button paymentsButton;
    @FXML
    private Button chequesButton;
    @FXML
    private AnchorPane mainWindowPane;
    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane anchorPaneMainWindow;
    @FXML
    private BorderPane loadPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OrdersButtonAction(ActionEvent event) {
        colourChangeOrderButton();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/OrderWindow.fxml"));
            Pane orderPane = loader.load();
            loadPane.setLeft(orderPane);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void itemsButtonAction(ActionEvent event) {
        colourChangeItemsButton();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/ItemsWindow.fxml"));
            Pane orderPane = loader.load();
            loadPane.setLeft(orderPane);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void customersButtonAction(ActionEvent event) {
        colourChangeCustomersButton();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/CustomersWindow.fxml"));
            Pane orderPane = loader.load();
            loadPane.setLeft(orderPane);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void suppliersButtonAction(ActionEvent event) {
        colourChangeSuppliersButton();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/SuppliersWindow.fxml"));
            Pane orderPane = loader.load();
            loadPane.setLeft(orderPane);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void employeesButtonAction(ActionEvent event) {
        colourChangeEmployeesButton();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/EmployeesWindow.fxml"));
            Pane employeePane = loader.load();
            loadPane.setLeft(employeePane);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void purchOrdersButtonAction(ActionEvent event) {
        colourChangePurchOrdersButton();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/PurchOrderWindow.fxml"));
            Pane orderPane = loader.load();
            loadPane.setLeft(orderPane);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void paymentsButtonAction(ActionEvent event) {
        colourChangePaymentsButton();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/PaymentsWindow.fxml"));
            Pane orderPane = loader.load();
            loadPane.setLeft(orderPane);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void chequesButtonAction(ActionEvent event) {
    }

    @FXML
    private void exitButtonAction(ActionEvent event) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit System");
        alert.setHeaderText("Do you want to exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            // close the dialog
        }
    }

    private void colourChangeOrderButton() {
        ordersButton.setTextFill(Paint.valueOf("blue"));
        itemsButton.setTextFill(Paint.valueOf("#00b0ff"));
        customersButton.setTextFill(Paint.valueOf("#00b0ff"));
        suppliersButton.setTextFill(Paint.valueOf("#00b0ff"));
        employeesButton.setTextFill(Paint.valueOf("#00b0ff"));
        purchOrdersButton.setTextFill(Paint.valueOf("#00b0ff"));
        paymentsButton.setTextFill(Paint.valueOf("#00b0ff"));
    }

    private void colourChangeItemsButton() {
        ordersButton.setTextFill(Paint.valueOf("#00b0ff"));
        itemsButton.setTextFill(Paint.valueOf("blue"));
        customersButton.setTextFill(Paint.valueOf("#00b0ff"));
        suppliersButton.setTextFill(Paint.valueOf("#00b0ff"));
        employeesButton.setTextFill(Paint.valueOf("#00b0ff"));
        purchOrdersButton.setTextFill(Paint.valueOf("#00b0ff"));
        paymentsButton.setTextFill(Paint.valueOf("#00b0ff"));
    }

    private void colourChangeCustomersButton() {
        ordersButton.setTextFill(Paint.valueOf("#00b0ff"));
        itemsButton.setTextFill(Paint.valueOf("#00b0ff"));
        customersButton.setTextFill(Paint.valueOf("blue"));
        suppliersButton.setTextFill(Paint.valueOf("#00b0ff"));
        employeesButton.setTextFill(Paint.valueOf("#00b0ff"));
        purchOrdersButton.setTextFill(Paint.valueOf("#00b0ff"));
        paymentsButton.setTextFill(Paint.valueOf("#00b0ff"));
    }

    private void colourChangeSuppliersButton() {
        ordersButton.setTextFill(Paint.valueOf("#00b0ff"));
        itemsButton.setTextFill(Paint.valueOf("#00b0ff"));
        customersButton.setTextFill(Paint.valueOf("#00b0ff"));
        suppliersButton.setTextFill(Paint.valueOf("blue"));
        employeesButton.setTextFill(Paint.valueOf("#00b0ff"));
        purchOrdersButton.setTextFill(Paint.valueOf("#00b0ff"));
        paymentsButton.setTextFill(Paint.valueOf("#00b0ff"));
    }

    private void colourChangeEmployeesButton() {
        ordersButton.setTextFill(Paint.valueOf("#00b0ff"));
        itemsButton.setTextFill(Paint.valueOf("#00b0ff"));
        customersButton.setTextFill(Paint.valueOf("#00b0ff"));
        suppliersButton.setTextFill(Paint.valueOf("#00b0ff"));
        employeesButton.setTextFill(Paint.valueOf("blue"));
        purchOrdersButton.setTextFill(Paint.valueOf("#00b0ff"));
        paymentsButton.setTextFill(Paint.valueOf("#00b0ff"));
    }

    private void colourChangePurchOrdersButton() {
        ordersButton.setTextFill(Paint.valueOf("#00b0ff"));
        itemsButton.setTextFill(Paint.valueOf("#00b0ff"));
        customersButton.setTextFill(Paint.valueOf("#00b0ff"));
        suppliersButton.setTextFill(Paint.valueOf("#00b0ff"));
        employeesButton.setTextFill(Paint.valueOf("#00b0ff"));
        purchOrdersButton.setTextFill(Paint.valueOf("blue"));
        paymentsButton.setTextFill(Paint.valueOf("#00b0ff"));
    }

    private void colourChangePaymentsButton() {
        ordersButton.setTextFill(Paint.valueOf("#00b0ff"));
        itemsButton.setTextFill(Paint.valueOf("#00b0ff"));
        customersButton.setTextFill(Paint.valueOf("#00b0ff"));
        suppliersButton.setTextFill(Paint.valueOf("#00b0ff"));
        employeesButton.setTextFill(Paint.valueOf("#00b0ff"));
        purchOrdersButton.setTextFill(Paint.valueOf("#00b0ff"));
        paymentsButton.setTextFill(Paint.valueOf("blue"));
    }

}
