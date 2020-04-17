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
import com.trousershop.daoImpl.ItemsDAOImpl;
import com.trousershop.daointerface.ItemsDAO;
import com.trousershop.dto.ItemsDTO;
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
public class ItemsWindowController implements Initializable {

    private ItemsDAO itemsDAO = new ItemsDAOImpl();
    private ObservableList<ItemsDTO> items = FXCollections.observableArrayList();
    private FilteredList<ItemsDTO> filteredList;
    private SortedList<ItemsDTO> sortedList;
    
    String saveButtonStatus = "disabled";
    
    @FXML
    private TableView<ItemsDTO> itemTable;
    @FXML
    private TableColumn<ItemsDTO, String> itemCode;
    @FXML
    private TableColumn<ItemsDTO, String> itemDescription;
    @FXML
    private TableColumn<ItemsDTO, Double> unitPrice;
    @FXML
    private TableColumn<ItemsDTO, Double> discount;
    @FXML
    private TableColumn<ItemsDTO, Integer> qtyOnHand;
    @FXML
    private TableColumn<ItemsDTO, String> addedDate;
    @FXML
    private TableColumn<ItemsDTO, String> note;
    @FXML
    private JFXComboBox<String> searchByCombo;
    @FXML
    private JFXTextField txtDescription;
    @FXML
    private JFXTextField txtUnitPrice;
    @FXML
    private JFXTextField txtQtyOnHand;
    @FXML
    private JFXTextField txtDiscount;
    @FXML
    private JFXTextField txtItemCode;
    @FXML
    private Pane dataPane;
    @FXML
    private JFXTextArea txtNotes;
    @FXML
    private JFXDatePicker dateAdedDate;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton newButton;
    @FXML
    private JFXButton editButton;
    @FXML
    private JFXButton deleteButton;
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        loadTable();
        loadComboBox();
        //itemTable.loadTable(getItems());
    }

    private void loadTable() {
        items.clear();
        saveButtonStatus = "disabled";
        try {
            ArrayList<ItemsDTO> all = itemsDAO.getAll();
            for (ItemsDTO items : all) {
                this.items.add(items);
            }
            filteredList = new FilteredList<>(items,p -> true);
            
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue)-> {
                String filter = searchByCombo.getValue();
                
                filteredList.setPredicate((ItemsDTO item) -> {
                    if(currentValue == null || currentValue.isEmpty()){
                        return true;
                    }
                    if(filter != null){
                        if(filter.equals("Item Code")){
                            return (item.getCode().contains(currentValue));
                        }else if(filter.equals("Description")){
                            return item.getDescription().toLowerCase().contains(currentValue.toLowerCase());
                        }else{
                            return false;
                        }
                    }else{
                        return true;
                    }
                });
            });
            
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(itemTable.comparatorProperty());
            itemTable.setItems(sortedList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    } 
   
    private void initColumns() {
        itemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        qtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        addedDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        note.setCellValueFactory(new PropertyValueFactory<>("notes"));
    }

    private void loadComboBox() {
        ObservableList<String> comboItems = FXCollections.observableArrayList("Item Code","Description");
        searchByCombo.getItems().setAll(comboItems);
    }
    

    @FXML
    private void save(ActionEvent event) {
        try {
            ItemsDTO customer = new ItemsDTO(
                    txtItemCode.getText(), 
                    txtDescription.getText(), 
                    Double.parseDouble(txtUnitPrice.getText()), 
                    Double.parseDouble(txtDiscount.getText()), 
                    Integer.parseInt(txtQtyOnHand.getText()), 
                    dateAdedDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), 
                    txtNotes.getText());

            switch (saveButtonStatus) {
                case "new":
                    boolean saved = false;
                    saved = itemsDAO.add(customer);
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
                    txtItemCode.setText(null);
                    clearFields();

                    break;
                case "edit":
                    boolean updated = false;
                    updated = itemsDAO.update(customer);
                    if (updated) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Item");
                        alert.setHeaderText("Successfully Updated");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Update Item");
                        alert.setHeaderText("Item wasn't Updateed");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
                    loadTable();
                    dataPane.setDisable(true);
                    txtItemCode.setText(null);
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
        txtItemCode.setText(null);
        clearFields();
    }

    @FXML
    private void newButton(ActionEvent event) {
        saveButtonStatus = "new";
        dataPane.setDisable(false);
        try {
            txtItemCode.setText(itemsDAO.getNextID());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        ItemsDTO selected = itemTable.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Do you want to delete selected Item?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            try {
            boolean deleted = itemsDAO.delete(selected);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        } else {
            // close the dialog
        }
        dataPane.setDisable(true);
        loadTable();
    }

    @FXML
    private void editButton(ActionEvent event) {
        saveButtonStatus = "edit";
        dataPane.setDisable(false);
        ItemsDTO selected = itemTable.getSelectionModel().getSelectedItem();
        
        txtItemCode.setText(selected.getCode());
        txtDescription.setText(selected.getDescription());
        txtUnitPrice.setText(selected.getUnitPrice()+"");
        txtDiscount.setText(selected.getDiscount()+"");
        txtQtyOnHand.setText(selected.getQtyOnHand()+"");
        txtNotes.setText(selected.getNotes());
        dateAdedDate.setValue(LocalDate.parse(selected.getAddedDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        
    }

    private void clearFields() {
        txtDescription.setText(null);
        txtUnitPrice.setText(null);
        txtDiscount.setText(null);
        txtQtyOnHand.setText(null);
        txtNotes.setText(null);
        dateAdedDate.setValue(null);
    }

}
