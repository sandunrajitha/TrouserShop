/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.trousershop.daoImpl.PurchaseOrderDAOImpl;
import com.trousershop.daointerface.PurchaseOrderDAO;
import com.trousershop.dto.PurchaseOrderDTO;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Sandun
 */
public class PurchOrderWindowController implements Initializable {

    ObservableList<PurchaseOrderDTO> list = FXCollections.observableArrayList();
    private FilteredList<PurchaseOrderDTO> filteredList;
    private SortedList<PurchaseOrderDTO> sortedList;
    PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAOImpl();

    @FXML
    private AnchorPane orderWindow;
    @FXML
    private TableView<PurchaseOrderDTO> purchOrderTable;
    @FXML
    private TableColumn<PurchaseOrderDTO, String> orderID;
    @FXML
    private TableColumn<PurchaseOrderDTO, String> supID;
    @FXML
    private TableColumn<PurchaseOrderDTO, String> addedDate;
    @FXML
    private TableColumn<PurchaseOrderDTO, String> dueDate;
    @FXML
    private TableColumn<PurchaseOrderDTO, Double> amount;
    @FXML
    private JFXComboBox<String> searchByCombo;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXButton payments;
    @FXML
    private JFXButton addPurchOrder;
    @FXML
    private JFXButton editPurchOrder;
    @FXML
    private JFXButton deletePurchOrder;
    @FXML
    private Pane PurchOrderPane;
    @FXML
    private JFXButton savePurchOrder;
    @FXML
    private JFXButton cancel;
    @FXML
    private Pane orderDetailPane;
    @FXML
    private TableColumn<?, ?> code;
    @FXML
    private TableColumn<?, ?> description;
    @FXML
    private TableColumn<?, ?> qty;
    @FXML
    private TableColumn<?, ?> unitPrice;
    @FXML
    private TableColumn<?, ?> amount1;
    @FXML
    private JFXButton addDetail;
    @FXML
    private JFXButton editDetail;
    @FXML
    private JFXButton deleteDetail;
    @FXML
    private JFXButton saveOrder;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        loadTable();
        loadComboBox();
    }

    private void initColumns() {
        orderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        supID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        addedDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void loadTable() {
        try {
            ArrayList<PurchaseOrderDTO> all = purchaseOrderDAO.getAll();
            for (PurchaseOrderDTO purchaseOrders : all) {
                this.list.add(purchaseOrders);
            }
            filteredList = new FilteredList<>(list, p -> true);
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {
                String filter = searchByCombo.getValue();

                filteredList.setPredicate((PurchaseOrderDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    }
                    if (filter != null) {
                        if (filter.equals("Order ID")) {
                            return (item.getId().contains(currentValue));
                        } else if (filter.equals("Supplier ID")) {
                            return item.getSupplierID().toLowerCase().contains(currentValue.toLowerCase());
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                });
            });
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(purchOrderTable.comparatorProperty());
            purchOrderTable.setItems(sortedList);
            //purchOrderTable.getItems().setAll(list);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void loadComboBox() {
        ObservableList<String> comboItems = FXCollections.observableArrayList("Order ID","Supplier ID");
        searchByCombo.getItems().setAll(comboItems);
    }

    @FXML
    private void payments(ActionEvent event) {
    }

    @FXML
    private void addPurchOrder(ActionEvent event) {
    }

    @FXML
    private void editPurchOrder(ActionEvent event) {
    }

    @FXML
    private void deletePurchOrder(ActionEvent event) {
    }

    @FXML
    private void savePurchOrder(ActionEvent event) {
    }

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void addDetail(ActionEvent event) {
    }

    @FXML
    private void editDetail(ActionEvent event) {
    }

    @FXML
    private void deleteDetail(ActionEvent event) {
    }

    @FXML
    private void saveOrder(ActionEvent event) {
    }
}
