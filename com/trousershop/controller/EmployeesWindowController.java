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
import com.trousershop.daoImpl.EmployeeDAOImpl;
import com.trousershop.daointerface.EmployeeDAO;
import com.trousershop.dto.EmployeeDTO;
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
public class EmployeesWindowController implements Initializable {

    EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    ObservableList<EmployeeDTO> list = FXCollections.observableArrayList();
    private FilteredList<EmployeeDTO> filteredList;
    private SortedList<EmployeeDTO> sortedList;
    String saveButtonStatus = "disabled";

    @FXML
    private TableView<EmployeeDTO> employeeTable;
    @FXML
    private TableColumn<EmployeeDTO, String> id;
    @FXML
    private TableColumn<EmployeeDTO, String> name;
    @FXML
    private TableColumn<EmployeeDTO, Integer> nicNo;
    @FXML
    private TableColumn<EmployeeDTO, String> dob;
    @FXML
    private TableColumn<EmployeeDTO, String> address;
    @FXML
    private TableColumn<EmployeeDTO, Integer> salary;
    @FXML
    private TableColumn<EmployeeDTO, Integer> mobile;
    @FXML
    private TableColumn<EmployeeDTO, Integer> phone;
    @FXML
    private TableColumn<EmployeeDTO, String> joinedDate;
    @FXML
    private TableColumn<EmployeeDTO, String> notes;
    @FXML
    private JFXComboBox<String> searchByCombo;
    @FXML
    private Pane dataPane;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtNic;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXDatePicker dateJoinedDate;
    @FXML
    private JFXTextField txtMobile;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField txtEmpID;
    @FXML
    private JFXDatePicker dateDob;
    @FXML
    private JFXTextField txtSalary;
    @FXML
    private JFXButton newButton;
    @FXML
    private JFXButton editButton;
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

    private void initColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        nicNo.setCellValueFactory(new PropertyValueFactory<>("nic"));
        dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        joinedDate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        notes.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    private void loadTable() {
        saveButtonStatus = "disabled";
        list.clear();
        try {
            ArrayList<EmployeeDTO> employees = employeeDAO.getAll();

            for (EmployeeDTO employee : employees) {
                list.add(employee);
            }
            filteredList = new FilteredList<>(list, p -> true);
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {
                String filter = searchByCombo.getValue();

                filteredList.setPredicate((EmployeeDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    }
                    if (filter != null) {
                        if (filter.equals("Employee ID")) {
                            return (item.getId().contains(currentValue));
                        } else if (filter.equals("Employee Name")) {
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
            sortedList.comparatorProperty().bind(employeeTable.comparatorProperty());
            employeeTable.setItems(sortedList);
            //employeeTable.getItems().setAll(list);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void loadComboBox() {
        ObservableList<String> comboItems = FXCollections.observableArrayList("Employee ID", "Employee Name");
        searchByCombo.getItems().setAll(comboItems);
    }

    @FXML
    private void saveButton(ActionEvent event) {
        try {
            EmployeeDTO employee = new EmployeeDTO(
                    txtEmpID.getText(), 
                    txtName.getText(), 
                    Integer.parseInt(txtNic.getText()), 
                    dateDob.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 
                    txtAddress.getText(), 
                    Double.parseDouble(txtSalary.getText()), 
                    Integer.parseInt(txtMobile.getText()), 
                    Integer.parseInt(txtPhone.getText()),                     
                    dateJoinedDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 
                    txtNotes.getText());

            switch (saveButtonStatus) {
                case "new":
                    boolean saved = false;
                    saved = employeeDAO.add(employee);
                    if (saved) {
                        System.out.println("if");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Employee");
                        alert.setHeaderText("Successfully Added");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Employee");
                        alert.setHeaderText("Employee wasn't added");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    loadTable();
                    dataPane.setDisable(true);
                    txtEmpID.setText(null);
                    clearFields();

                    break;
                case "edit":
                    boolean updated = false;
                    updated = employeeDAO.update(employee);
                    if (updated) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Employee");
                        alert.setHeaderText("Successfully Updated");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Employee");
                        alert.setHeaderText("Employee wasn't Updateed");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    loadTable();
                    dataPane.setDisable(true);
                    txtEmpID.setText(null);
                    clearFields();
                    break;
            }
        } catch (Exception ex) {
            System.out.println("emp window controller" + ex.toString());
        }
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        dataPane.setDisable(true);
        txtEmpID.setText(null);
        clearFields();
    }

    @FXML
    private void newButton(ActionEvent event) {
        saveButtonStatus = "new";
        clearFields();
        try {
            dataPane.setDisable(false);
            txtEmpID.setText(employeeDAO.getNextID());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void editButton(ActionEvent event) {
        saveButtonStatus = "edit";
        dataPane.setDisable(false);
        EmployeeDTO selected = employeeTable.getSelectionModel().getSelectedItem();

        txtEmpID.setText(selected.getId());
        txtName.setText(selected.getName());
        txtNic.setText(selected.getNic() + "");
        dateDob.setValue(LocalDate.parse(selected.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txtAddress.setText(selected.getAddress());
        txtSalary.setText(selected.getSalary()+"");
        txtMobile.setText(selected.getMobile() + "");
        txtPhone.setText(selected.getPhone() + "");
        txtNotes.setText(selected.getNotes());
        dateJoinedDate.setValue(LocalDate.parse(selected.getJoinedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        EmployeeDTO selected = employeeTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Employee");
        alert.setHeaderText("Do you want to delete selected employee?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            try {
                boolean deleted = employeeDAO.delete(selected);
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
        txtNic.setText(null);
        dateDob.setValue(null);
        txtAddress.setText(null);
        txtSalary.setText(null);
        txtMobile.setText(null);
        txtPhone.setText(null);
        dateJoinedDate.setValue(null);
        txtEmpID.setText(null);
        txtNotes.setText(null);
    }
}
