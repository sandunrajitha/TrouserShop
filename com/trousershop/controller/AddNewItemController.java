/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.trousershop.daoImpl.ItemsDAOImpl;
import com.trousershop.daointerface.ItemsDAO;
import com.trousershop.dto.ItemsDTO;
import java.net.URL;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rajitha
 */
public class AddNewItemController implements Initializable {

    private ItemsDAO itemsDAO = new ItemsDAOImpl();
    private ObservableList<ItemsDTO> items = FXCollections.observableArrayList();
    private FilteredList<ItemsDTO> filteredList;
    private SortedList<ItemsDTO> sortedList;

    //private ObservableList<ItemsDTO> detailTableList = FXCollections.observableArrayList();
    private ItemsDTO itemsDTO = new ItemsDTO();
    int qty;

    @FXML
    private JFXTextField txtSearch;
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
    private JFXButton addButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField txtQty;
    @FXML
    private JFXTextArea txtNotes;
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
    private JFXTextField txtAddedDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        loadTable();
    }

    @FXML
    private void addButton(ActionEvent event) {
        itemsDTO = null;
        itemsDTO = itemTable.getSelectionModel().getSelectedItem();
        qty = Integer.parseInt(txtQty.getText());

        if (itemsDTO != null) {
            if (txtQty.getText() != null && Integer.parseInt(txtQty.getText()) <= Integer.parseInt(txtQtyOnHand.getText())) {
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            } else if (Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add New Item");
                alert.setHeaderText("Order QTY is Larger than QTY at hand");
                Optional<ButtonType> result = alert.showAndWait();
            }
        }
    }

    public ItemsDTO getItem() {
        return itemsDTO;
    }

    public int getQTY() {
        return qty;
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void loadTable() {
        items.clear();
        try {
            ArrayList<ItemsDTO> all = itemsDAO.getAll();
            for (ItemsDTO items : all) {
                this.items.add(items);
            }
            filteredList = new FilteredList<>(items, p -> true);

            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {

                filteredList.setPredicate((ItemsDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    } else {

                        return (item.getCode().contains(currentValue.toUpperCase()));
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

    @FXML
    private void onClick(MouseEvent event) {
        ItemsDTO selected = itemTable.getSelectionModel().getSelectedItem();

        txtItemCode.setText(selected.getCode());
        txtDescription.setText(selected.getDescription());
        txtUnitPrice.setText(selected.getUnitPrice() + "");
        txtQtyOnHand.setText(selected.getQtyOnHand() + "");
        txtDiscount.setText(selected.getDiscount() + "");
        txtAddedDate.setText(selected.getAddedDate());
    }

}
