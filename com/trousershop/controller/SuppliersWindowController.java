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
import com.trousershop.daoImpl.SupplierDAOImpl;
import com.trousershop.daointerface.SupplierDAO;
import com.trousershop.dto.SupplierDTO;
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
public class SuppliersWindowController implements Initializable {

    SupplierDAO supplierDAO = new SupplierDAOImpl();
    ObservableList<SupplierDTO> list = FXCollections.observableArrayList();
    private FilteredList<SupplierDTO> filteredList;
    private SortedList<SupplierDTO> sortedList;
    String saveButtonStatus = "disabled";

    @FXML
    private TableView<SupplierDTO> suppliersTable;

    @FXML
    private TableColumn<SupplierDTO, String> id;
    @FXML
    private TableColumn<SupplierDTO, String> name;
    @FXML
    private TableColumn<SupplierDTO, String> address;
    @FXML
    private TableColumn<SupplierDTO, Integer> mobile;
    @FXML
    private TableColumn<SupplierDTO, Integer> phone;
    @FXML
    private TableColumn<SupplierDTO, String> addedDate;
    @FXML
    private TableColumn<SupplierDTO, String> note;
    @FXML
    private JFXComboBox<String> searchByCombo;
    @FXML
    private Pane dataPane;
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
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXButton newButton;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton deleteButton;
    @FXML
    private JFXTextField txtSupplierID;
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

    private void initColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addedDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        note.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    private void loadTable() {
        list.clear();
        saveButtonStatus = "disabled";
        try {
            ArrayList<SupplierDTO> all = supplierDAO.getAll();
            for (SupplierDTO suppliers : all) {
                this.list.add(suppliers);
            }
            filteredList = new FilteredList<>(list, p -> true);
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {
                String filter = searchByCombo.getValue();

                filteredList.setPredicate((SupplierDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    }
                    if (filter != null) {
                        if (filter.equals("Supplier ID")) {
                            return (item.getId().contains(currentValue));
                        } else if (filter.equals("Name")) {
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
            sortedList.comparatorProperty().bind(suppliersTable.comparatorProperty());
            suppliersTable.setItems(sortedList);
            //suppliersTable.getItems().setAll(list);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void loadComboBox() {
        ObservableList<String> comboItems = FXCollections.observableArrayList("Supplier ID" , "Name");
        searchByCombo.getItems().setAll(comboItems);
    }

    @FXML
    private void save(ActionEvent event) {
        try {
            SupplierDTO customer = new SupplierDTO(
                    txtSupplierID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    Integer.parseInt(txtMobile.getText()),
                    Integer.parseInt(txtPhone.getText()),
                    txtAddedDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    txtNotes.getText());

            switch (saveButtonStatus) {
                case "new":
                    boolean saved = false;
                    saved = supplierDAO.add(customer);
                    if (saved) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Supplier");
                        alert.setHeaderText("Successfully Added");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Supplier");
                        alert.setHeaderText("Supplier wasn't added");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    loadTable();
                    dataPane.setDisable(true);
                    txtSupplierID.setText(null);
                    clearFields();

                    break;
                case "edit":
                    boolean updated = false;
                    updated = supplierDAO.update(customer);
                    if (updated) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Supplier");
                        alert.setHeaderText("Successfully Updated");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Supplier");
                        alert.setHeaderText("Supplier wasn't Updateed");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    loadTable();
                    dataPane.setDisable(true);
                    txtSupplierID.setText(null);
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
        txtSupplierID.setText(null);
        clearFields();
    }

    @FXML
    private void newButton(ActionEvent event) {
        saveButtonStatus = "new";
        clearFields();
        try {
            dataPane.setDisable(false);
            txtSupplierID.setText(supplierDAO.getNextID());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void editButton(ActionEvent event) {
        saveButtonStatus = "edit";
        dataPane.setDisable(false);
        SupplierDTO selected = suppliersTable.getSelectionModel().getSelectedItem();

        txtSupplierID.setText(selected.getId());
        txtName.setText(selected.getName());
        txtAddress.setText(selected.getAddress());
        txtMobile.setText(selected.getMobile() + "");
        txtPhone.setText(selected.getPhone() + "");
        txtNotes.setText(selected.getNotes());
        txtAddedDate.setValue(LocalDate.parse(selected.getAddedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    @FXML
    private void deleteButton(ActionEvent event) {
        SupplierDTO selected = suppliersTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Supplier");
        alert.setHeaderText("Do you want to delete selected supplier?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                boolean deleted = supplierDAO.delete(selected);
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
