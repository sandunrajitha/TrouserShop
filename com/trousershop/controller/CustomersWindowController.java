/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.trousershop.daoImpl.CustomerDAOImpl;
import com.trousershop.daointerface.CustomerDAO;
import com.trousershop.dto.CustomerDTO;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Sandun
 */
public class CustomersWindowController implements Initializable {

    private static CustomerDAO customerDAO = new CustomerDAOImpl();
    ObservableList<CustomerDTO> list = FXCollections.observableArrayList();
    private FilteredList<CustomerDTO> filteredList;
    private SortedList<CustomerDTO> sortedList;

    String saveButtonStatus = "disabled";

    @FXML
    private TableColumn<CustomerDTO, String> id;
    @FXML
    private TableColumn<CustomerDTO, String> name;
    @FXML
    private TableColumn<CustomerDTO, String> address;
    @FXML
    private TableColumn<CustomerDTO, Integer> mobile;
    @FXML
    private TableColumn<CustomerDTO, Integer> phone;
    @FXML
    private TableColumn<CustomerDTO, String> addedDate;
    @FXML
    private TableColumn<CustomerDTO, String> note;
    @FXML
    private TableView<CustomerDTO> customersTable;
    @FXML
    private JFXComboBox<String> searchByCombo;
    @FXML
    private Pane dataPane;
    @FXML
    private JFXButton newButton;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXDatePicker txtAddedDate;
    @FXML
    private JFXTextField txtCustomerID;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXTextField txtSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        loadTable();
        loadComboBox();

    }

    public void loadTable() {
        list.clear();
        saveButtonStatus = "disabled";
        try {
            ArrayList<CustomerDTO> all = customerDAO.getAll();

            for (CustomerDTO customers : all) {
                this.list.addAll(customers);
            }
            filteredList = new FilteredList<>(list, p -> true);
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {
                String filter = searchByCombo.getValue();

                filteredList.setPredicate((CustomerDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    }
                    if (filter != null) {
                        if (filter.equals("Customer ID")) {
                            return (item.getId().contains(currentValue));
                        } else if (filter.equals("Customer Name")) {
                            return item.getName().toLowerCase().contains(currentValue.toLowerCase());
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                });
            });
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(customersTable.comparatorProperty());
            customersTable.setItems(sortedList);
            //customersTable.getItems().setAll(list);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    private void initColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addedDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        note.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    private void loadComboBox() {
        ObservableList<String> comboItems = FXCollections.observableArrayList("Customer ID", "Customer Name");
        searchByCombo.getItems().setAll(comboItems);
    }

    @FXML
    private void newButton(ActionEvent event) {
        saveButtonStatus = "new";
        clearFields();
        try {
            dataPane.setDisable(false);
            txtCustomerID.setText(customerDAO.getNextID());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void editButton(ActionEvent event) {
        saveButtonStatus = "edit";
        dataPane.setDisable(false);
        CustomerDTO selected = customersTable.getSelectionModel().getSelectedItem();

        txtCustomerID.setText(selected.getId());
        txtName.setText(selected.getName());
        txtAddress.setText(selected.getAddress());
        txtMobile.setText(selected.getMobile() + "");
        txtPhone.setText(selected.getPhone() + "");
        txtNotes.setText(selected.getNotes());
        txtAddedDate.setValue(LocalDate.parse(selected.getAddedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    @FXML
    private void save(ActionEvent event) {
        try {
            CustomerDTO customer = new CustomerDTO(
                    txtCustomerID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Integer.parseInt(txtMobile.getText()),
                    Integer.parseInt(txtPhone.getText()),
                    txtAddedDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    txtNotes.getText());

            switch (saveButtonStatus) {
                case "new":
                    boolean saved = false;
                    saved = customerDAO.add(customer);
                    if (saved) {
                        System.out.println("if");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Customer");
                        alert.setHeaderText("Successfully Added");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Customer");
                        alert.setHeaderText("Customer wasn't added");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    loadTable();
                    dataPane.setDisable(true);
                    txtCustomerID.setText(null);
                    clearFields();

                    break;
                case "edit":
                    boolean updated = false;
                    updated = customerDAO.update(customer);
                    if (updated) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Customer");
                        alert.setHeaderText("Successfully Updated");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Customer");
                        alert.setHeaderText("Customer wasn't Updateed");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    loadTable();
                    dataPane.setDisable(true);
                    txtCustomerID.setText(null);
                    clearFields();
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
        dataPane.setDisable(true);
        txtCustomerID.setText(null);
        clearFields();
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        CustomerDTO selected = customersTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Do you want to delete selected customer?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                boolean deleted = customerDAO.delete(selected);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        } else {
            // close the dialog
        }
        dataPane.setDisable(true);
        loadTable();
    }

    private void clearFields() {
        txtName.setText(null);
        txtAddress.setText(null);
        txtMobile.setText(null);
        txtPhone.setText(null);
        txtNotes.setText(null);
        txtAddedDate.setValue(null);
    }

}