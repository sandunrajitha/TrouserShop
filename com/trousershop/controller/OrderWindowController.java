/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.trousershop.daoImpl.CustomerDAOImpl;
import com.trousershop.daoImpl.CustomerOrderDAOImpl;
import com.trousershop.daoImpl.CustomerOrderDetailsDAOImpl;
import com.trousershop.daoImpl.ItemsDAOImpl;
import com.trousershop.daointerface.CustomerDAO;
import com.trousershop.daointerface.CustomerOrderDetailsDAO;
import com.trousershop.daointerface.CustomerOrdersDAO;
import com.trousershop.dto.CustomerDTO;
import com.trousershop.dto.CustomerOrderDetailsDTO;
import com.trousershop.dto.CustomerOrdersDTO;
import com.trousershop.dto.ItemsDTO;
import com.trousershop.dto.OrderDetailsTableDTO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.soap.Detail;

/**
 * FXML Controller class
 *
 * @author Sandun
 */
public class OrderWindowController implements Initializable {

    ObservableList<OrderDetailsTableDTO> detailTableList = FXCollections.observableArrayList();

    CustomerOrderDetailsDAO customerOrderDetailsDAO = new CustomerOrderDetailsDAOImpl();

    OrderDetailsTableDTO orderDetailsTableDTO = new OrderDetailsTableDTO();

    ItemsDAOImpl itemsDAOImpl = new ItemsDAOImpl();

    CustomerOrdersDAO customerOrdersDAO = new CustomerOrderDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    private FilteredList<CustomerOrdersDTO> filteredList;
    private SortedList<CustomerOrdersDTO> sortedList;
    ObservableList<CustomerOrdersDTO> list = FXCollections.observableArrayList();
    String saveButtonAction = "disabled";
    String printButtonAction = "disabled";

    CustomerOrdersDTO selected;
    OrderDetailsTableDTO selectedDetail;

    @FXML
    private AnchorPane orderWindow;
    @FXML
    private TableColumn<CustomerOrdersDTO, String> id;
    @FXML
    private TableColumn<CustomerOrdersDTO, String> cusID;
    @FXML
    private TableColumn<CustomerOrdersDTO, String> addedDate;
    @FXML
    private TableColumn<CustomerOrdersDTO, String> dueDate;
    @FXML
    private TableColumn<CustomerOrdersDTO, Double> discount;
    @FXML
    private TableColumn<CustomerOrdersDTO, Double> amount;
    @FXML
    private TableView<CustomerOrdersDTO> orderTable;
    @FXML
    private JFXComboBox<String> searchByCombo;
    @FXML
    private JFXTextField txtOrderID;
    @FXML
    private JFXComboBox<CustomerDTO> comboCustomer;
    @FXML
    private JFXDatePicker dateAddedDate;
    @FXML
    private JFXDatePicker dateDueDate;
    @FXML
    private JFXTextField txtDiscount;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXButton payments;
    @FXML
    private JFXButton addCusOrder;
    @FXML
    private JFXButton editCusOrder;
    @FXML
    private JFXButton deleteCusOrder;
    @FXML
    private Pane cusOrderPane;
    @FXML
    private JFXButton saveCusOrder;
    @FXML
    private JFXButton cancel;
    @FXML
    private Pane orderDetailPane;
    @FXML
    private JFXButton printOrder;
    @FXML
    private JFXTextField txtTotal;
    @FXML
    private JFXTextField txtOrderDiscount;
    @FXML
    private JFXTextField txtSubTotal;
    @FXML
    private TableView<OrderDetailsTableDTO> detailsTable;
    @FXML
    private TableColumn<OrderDetailsTableDTO, String> detailsCode;
    @FXML
    private TableColumn<OrderDetailsTableDTO, String> detailsDescription;
    @FXML
    private TableColumn<OrderDetailsTableDTO, Integer> detailsQTY;
    @FXML
    private TableColumn<OrderDetailsTableDTO, Double> detailsUnitPrice;
    @FXML
    private TableColumn<OrderDetailsTableDTO, Double> detailsAmount;
    @FXML
    private TableColumn<OrderDetailsTableDTO, Double> detailsDiscount;
    @FXML
    private TableColumn<OrderDetailsTableDTO, Double> detailsNetAmount;
    @FXML
    private JFXButton addItem;
    @FXML
    private JFXButton editItem;
    @FXML
    private JFXButton deleteItem;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initColumns();
        loadOrderTable();
        loadComboBox();
    }

    private void initColumns() {
        //order table
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        cusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        addedDate.setCellValueFactory(new PropertyValueFactory<>("addedDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        discount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        //details table
        detailsCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        detailsDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        detailsQTY.setCellValueFactory(new PropertyValueFactory<>("orderQTY"));
        detailsUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        detailsAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        detailsDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        detailsNetAmount.setCellValueFactory(new PropertyValueFactory<>("netAmount"));
    }

    private void loadOrderTable() {
        list.clear();
        try {
            ArrayList<CustomerOrdersDTO> all = customerOrdersDAO.getAll();

            for (CustomerOrdersDTO customerOrders : all) {
                this.list.add(customerOrders);
            }
            filteredList = new FilteredList<>(list, p -> true);
            txtSearch.textProperty().addListener((itemList, previousValue, currentValue) -> {
                String filter = searchByCombo.getValue();

                filteredList.setPredicate((CustomerOrdersDTO item) -> {
                    if (currentValue == null || currentValue.isEmpty()) {
                        return true;
                    }
                    if (filter != null) {
                        if (filter.equals("Order ID")) {
                            return (item.getId().contains(currentValue));
                        } else if (filter.equals("Customer ID")) {
                            return item.getCustomerID().toLowerCase().contains(currentValue.toLowerCase());
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                });
            });
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(orderTable.comparatorProperty());
            orderTable.setItems(sortedList);
            //orderTable.getItems().setAll(all);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void loadComboBox() {
        ObservableList<String> comboItems = FXCollections.observableArrayList("Order ID", "Customer ID");
        searchByCombo.getItems().setAll(comboItems);
    }

    @FXML
    private void payments(ActionEvent event) {
    }

    @FXML
    private void addCusOrder(ActionEvent event) {
        clearfields();
        cusOrderPane.setDisable(false);
        orderDetailPane.setDisable(false);
        saveButtonAction = "new";
        try {
            txtOrderID.setText(customerOrdersDAO.getNextID());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        try {
            loadCusCombo();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @FXML
    private void editCusOrder(ActionEvent event) {
        cusOrderPane.setDisable(false);
        orderDetailPane.setDisable(false);
        saveButtonAction = "edit";

        CustomerDTO selectedCustomer = new CustomerDTO();
        CustomerOrdersDTO selected = orderTable.getSelectionModel().getSelectedItem();
        try {
            selectedCustomer = customerDAO.search(selected.getCustomerID());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        loadCusCombo();
        comboCustomer.getSelectionModel().selectFirst();
        comboCustomer.getSelectionModel().select(selectedCustomer);
        txtOrderID.setText(selected.getId());
        dateAddedDate.setValue(LocalDate.parse(selected.getAddedDate()));
        dateDueDate.setValue(LocalDate.parse(selected.getDueDate()));
        txtDiscount.setText("" + selected.getDiscount());
    }

    @FXML
    private void deleteCusOrder(ActionEvent event) {
        try {
            CustomerOrdersDTO selected = orderTable.getSelectionModel().getSelectedItem();
            System.out.println(selected.getId());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer Order");
            alert.setHeaderText("Do you want to delete order " + selected.getId() + " ?");
            Optional<ButtonType> result = alert.showAndWait();
            boolean deleted = false;

            if (result.get() == ButtonType.OK) {
                try {
                    deleted = customerOrdersDAO.delete(selected);
                    if (deleted) {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Delete Customer Order");
                        alert2.setHeaderText("Successfully Deleted Order ID" + selected.getId());
                        Optional<ButtonType> result2 = alert.showAndWait();
                    } else {
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Delete Customer Order");
                        alert2.setHeaderText("Delete Unsuccessful Order ID" + selected.getId());
                        Optional<ButtonType> result2 = alert.showAndWait();
                    }
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            } else {
                // close the dialog
            }

        } catch (Exception ex) {
            Logger.getLogger(OrderWindowController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            loadOrderTable();
        }

    }

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void printOrder(ActionEvent event) {
    }

    @FXML
    private void saveCusOrder(ActionEvent event) {
        try {
            CustomerOrdersDTO customerOrdersDTO = new CustomerOrdersDTO(
                    txtOrderID.getText(),
                    comboCustomer.getSelectionModel().getSelectedItem().getId(),
                    dateAddedDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    dateDueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    Double.parseDouble(txtDiscount.getText()),
                    Double.parseDouble(txtSubTotal.getText()));

            switch (saveButtonAction) {
                case "new":
                    boolean saved = false;
                    saved = customerOrdersDAO.add(customerOrdersDTO);

                    if (saved) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Customer Order");
                        alert.setHeaderText("Successfully Added");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Add Customer Order");
                        alert.setHeaderText("Add Order Failed");
                        Optional<ButtonType> result = alert.showAndWait();
                    }

                case "edit":
                    boolean edited = false;
                    edited = customerOrdersDAO.update(customerOrdersDTO);

                    if (edited) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Edit Customer Order");
                        alert.setHeaderText("Successfully Edited");
                        Optional<ButtonType> result = alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Edit Customer Order");
                        alert.setHeaderText("Editing Order Failed");
                        Optional<ButtonType> result = alert.showAndWait();
                    }
            }
            loadOrderTable();
            clearfields();
            txtOrderID.setText(null);
            cusOrderPane.setDisable(false);
        } catch (Exception ex) {
            ex.toString();
        } finally {
            loadOrderDetailTable(selected);
        }
    }

    private void loadCusCombo() {
        ObservableList<CustomerDTO> customerComboItems = FXCollections.observableArrayList();
        try {
            ArrayList<CustomerDTO> comboCustoemers = customerDAO.getAll();
            for (CustomerDTO customer : comboCustoemers) {
                customerComboItems.add(customer);
            }
            comboCustomer.setItems(customerComboItems);
        } catch (Exception ex) {
            Logger.getLogger(OrderWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearfields() {

        comboCustomer.setValue(null);
        dateAddedDate.setValue(null);
        dateDueDate.setValue(null);
        txtDiscount.setText(null);

    }

    @FXML
    private void OnClick(MouseEvent event) {

        selected = orderTable.getSelectionModel().getSelectedItem();
        loadOrderDetailTable(selected);
    }

    private void loadOrderDetailTable(CustomerOrdersDTO selected) {
        try {
            ArrayList<OrderDetailsTableDTO> detailsTableDTOs = new ArrayList<>();

            detailTableList.clear();

            txtOrderDiscount.setText(selected.getDiscount() + "");

            ArrayList<CustomerOrderDetailsDTO> all = new ArrayList<>();

            all.clear();

            all = customerOrderDetailsDAO.getAll(selected);

            if (all != null) {
                detailsTableDTOs = orderDetailsTableDTO.getDetailsArrayList(all);

                for (OrderDetailsTableDTO orderDetail : detailsTableDTOs) {

                    detailTableList.add(orderDetail);

                }

            } else {
                detailTableList.clear();
            }
            detailsTable.getItems().setAll(detailTableList);

            updatePrice();
            orderDetailPane.setDisable(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void addItem(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/trousershop/view/AddNewItem.fxml"));
            Stage stage = new Stage();
            stage.initOwner(addItem.getScene().getWindow());
            stage.setScene(new Scene((Parent) loader.load()));
            stage.showAndWait();

            AddNewItemController controller = loader.getController();

            ItemsDTO newItem = controller.getItem();
            int newItemQTY = controller.getQTY();

            OrderDetailsTableDTO newOrderDetail = new OrderDetailsTableDTO(
                    orderTable.getSelectionModel().getSelectedItem().getId(),
                    newItem.getCode(),
                    newItem.getDescription(),
                    newItemQTY,
                    newItem.getUnitPrice(),
                    newItemQTY * newItem.getUnitPrice(),
                    newItem.getDiscount(),
                    ((newItemQTY * newItem.getUnitPrice()) / 100) * (100 - newItem.getDiscount()));

            if (detailTableList.contains(newOrderDetail)) {
                System.out.println("contains");

                OrderDetailsTableDTO existingDetail = detailTableList.get(detailTableList.indexOf(newOrderDetail));
                int QTY = existingDetail.getOrderQTY() + newItemQTY;
                existingDetail.setOrderQTY(QTY);
                existingDetail.setAmount(existingDetail.getUnitPrice() * QTY);
                existingDetail.setNetAmount(((existingDetail.getUnitPrice() * QTY) / 100) * (100 - existingDetail.getDiscount()));
                detailTableList.remove(newOrderDetail);
                detailTableList.add(existingDetail);
            } else {
                System.out.println("doenst contain");
                if (newItemQTY > 0) {
                    detailTableList.add(newOrderDetail);
                }
            }
            detailsTable.getItems().setAll(detailTableList);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            updatePrice();
        }

    }

    @FXML
    private void editItem(ActionEvent event) {
    }

    @FXML
    private void deleteItem(ActionEvent event) {
        selectedDetail = detailsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Do you want to delete selected item from the order?");
        Optional<ButtonType> result = alert.showAndWait();

        boolean deleted = false;

        if (result.get() == ButtonType.OK) {
            try {
                deleted = detailTableList.remove(selectedDetail);

            } catch (Exception ex) {
                System.out.println(ex.toString());
            } finally {
                detailsTable.getItems().setAll(detailTableList);
                updatePrice();
            }

        } else {
            // close the dialog
        }

    }

    public void updatePrice() {
        double total = 0;

        for (OrderDetailsTableDTO detail : detailsTable.getItems()) {
            total = total + detail.getNetAmount();
        }

        NumberFormat decimalFormat = new DecimalFormat("##.00");

        double subTotal = 0;
        subTotal = (total / 100) * (100 - selected.getDiscount());

        System.out.println(total);
        txtTotal.setText(decimalFormat.format(total));

        System.out.println(subTotal);
        txtSubTotal.setText(decimalFormat.format(subTotal));
    }

}
