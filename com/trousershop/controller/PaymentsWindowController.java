/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.trousershop.daoImpl.CustomerPaymentsDAOImpl;
import com.trousershop.daoImpl.SupplierPaymentsDAOImpl;
import com.trousershop.daointerface.CustomerPaymentsDAO;
import com.trousershop.daointerface.SupplierPaymentsDAO;
import com.trousershop.dto.CustomerDTO;
import com.trousershop.dto.CustomerPaymentsDTO;
import com.trousershop.dto.SuperDTO;
import com.trousershop.dto.SupplierPaymentsDTO;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author Sandun
 */
public class PaymentsWindowController implements Initializable {
    
    

    @FXML
    private JFXButton cusPayments;
    @FXML
    private JFXButton supPayments;
    @FXML
    private Pane loadPanePayments;
    @FXML
    private Pane CusPaymentsPane;
    @FXML
    private TableView<SuperDTO> paymentsTable;
    @FXML
    private TableColumn<SuperDTO, String> paymentID;
    @FXML
    private TableColumn<SuperDTO, String> orderID;
    @FXML
    private TableColumn<SuperDTO, String> date;
    @FXML
    private TableColumn<SuperDTO, Double> payment;
    @FXML
    private TableColumn<SuperDTO, Double> paidAmount;
    @FXML
    private TableColumn<SuperDTO, String> invoiceNo;
    @FXML
    private TableColumn<SuperDTO, String> method;
    @FXML
    private JFXComboBox<String> searchByCombo;
    @FXML
    private JFXTextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        loadComboBox();
    }

    @FXML
    private void cusPaymentsClick(ActionEvent event) {
        colourChangeCusPaymentsButton();
        loadCusPayments();

    }

    @FXML
    private void supPaymentsClick(ActionEvent event) {
        colourChangeSupPaymentsButton();
        loadSupPayments();
    }

    private void initColumns() {
        paymentID.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        paidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        invoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNo"));
        method.setCellValueFactory(new PropertyValueFactory<>("method"));
    }

    private void loadCusPayments() {
        try {
            ObservableList<CustomerPaymentsDTO> cusPayments = FXCollections.observableArrayList();
            FilteredList<CustomerPaymentsDTO> filteredList;
            SortedList<SuperDTO> sortedList;
            CustomerPaymentsDAO customerPaymentsDAO = new CustomerPaymentsDAOImpl();

            ArrayList<CustomerPaymentsDTO> allCustomerPayments = customerPaymentsDAO.getAll();
            
            for (CustomerPaymentsDTO customerPayments : allCustomerPayments) {
                cusPayments.add(customerPayments);
            }
            
            filteredList = new FilteredList<>(cusPayments, p -> true);
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {
                String filter = searchByCombo.getValue();

                filteredList.setPredicate((CustomerPaymentsDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    }
                    if (filter != null) {
                        if (filter.equals("Order ID")) {
                            return (item.getOrderID().contains(currentValue));
                        } else if (filter.equals("Invoice No")) {
                            return item.getInvoiceNo().toLowerCase().contains(currentValue.toLowerCase());
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                });
            });
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(paymentsTable.comparatorProperty());
            paymentsTable.setItems(sortedList);
            //paymentsTable.getItems().setAll(cusPayments);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void loadSupPayments() {
        try {
            ObservableList<SupplierPaymentsDTO> supPaymnets = FXCollections.observableArrayList();
            FilteredList<SupplierPaymentsDTO> filteredList;
            SortedList<SuperDTO> sortedList;
            SupplierPaymentsDAO supplierPaymentsDAO = new SupplierPaymentsDAOImpl();

            ArrayList<SupplierPaymentsDTO> supplierPayments = supplierPaymentsDAO.getAll();
            for (SupplierPaymentsDTO supplierPayment : supplierPayments) {
                supPaymnets.add(supplierPayment);
            }
            filteredList = new FilteredList<>(supPaymnets, p -> true);
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {
                String filter = searchByCombo.getValue();

                filteredList.setPredicate((SupplierPaymentsDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    }
                    if (filter != null) {
                        if (filter.equals("Order ID")) {
                            return (item.getOrderID().contains(currentValue));
                        } else if (filter.equals("Invoice No")) {
                            return item.getInvoiceNo().toLowerCase().contains(currentValue.toLowerCase());
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                });
            });
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(paymentsTable.comparatorProperty());
            paymentsTable.setItems(sortedList);
            //paymentsTable.getItems().setAll(supPaymnets);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void colourChangeSupPaymentsButton() {

        supPayments.setTextFill(Paint.valueOf("#0d47a1"));
        cusPayments.setTextFill(Paint.valueOf("white"));
    }

    private void colourChangeCusPaymentsButton() {

        supPayments.setTextFill(Paint.valueOf("white"));
        cusPayments.setTextFill(Paint.valueOf("#0d47a1"));
    }

    private void loadComboBox() {
        ObservableList<String> comboItems = FXCollections.observableArrayList("Order ID","Invoice No");
        searchByCombo.getItems().setAll(comboItems);
    }

}
