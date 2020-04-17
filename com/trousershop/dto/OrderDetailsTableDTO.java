/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.dto;

import com.trousershop.daoImpl.ItemsDAOImpl;
import com.trousershop.daointerface.ItemsDAO;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author rajitha
 */
public class OrderDetailsTableDTO {

    private String orderID;
    private String itemCode;
    private String description;
    private int orderQTY;
    private double unitPrice;
    private double amount;
    private double discount;
    private double netAmount;
    private ArrayList<OrderDetailsTableDTO> orderDetailsTableDTOs = new ArrayList<>();

    public OrderDetailsTableDTO() {
    }

    public OrderDetailsTableDTO(String orderID, String itemCode, String description, int orderQTY, double unitPrice, double amount, double discount, double netAmount) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.description = description;
        this.orderQTY = orderQTY;
        this.unitPrice = unitPrice;
        this.amount = amount;
        this.discount = discount;
        this.netAmount = netAmount;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.itemCode);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.unitPrice) ^ (Double.doubleToLongBits(this.unitPrice) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.discount) ^ (Double.doubleToLongBits(this.discount) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrderDetailsTableDTO other = (OrderDetailsTableDTO) obj;
        if (Double.doubleToLongBits(this.unitPrice) != Double.doubleToLongBits(other.unitPrice)) {
            return false;
        }
        if (Double.doubleToLongBits(this.discount) != Double.doubleToLongBits(other.discount)) {
            return false;
        }
        if (!Objects.equals(this.itemCode, other.itemCode)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderQTY() {
        return orderQTY;
    }

    public void setOrderQTY(int orderQTY) {
        this.orderQTY = orderQTY;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public ArrayList<OrderDetailsTableDTO> getDetailsArrayList(ArrayList<CustomerOrderDetailsDTO> orderDetailsDTOs) throws Exception {

        ItemsDAO itemsDAO = new ItemsDAOImpl();
        orderDetailsTableDTOs.clear();
        for (CustomerOrderDetailsDTO orderDetail : orderDetailsDTOs) {

            ItemsDTO item = itemsDAO.search(orderDetail.getItemCode());

            orderDetailsTableDTOs.add(new OrderDetailsTableDTO(
                    orderDetail.getOrderID(),
                    orderDetail.getItemCode(),
                    item.getDescription(),
                    orderDetail.getQty(),
                    item.getUnitPrice(),
                    (item.getUnitPrice() * orderDetail.getQty()),
                    item.getDiscount(),
                    (item.getUnitPrice() * orderDetail.getQty()) - ((item.getUnitPrice() * orderDetail.getQty()) / 100) * item.getDiscount()));

        }

        /*for (OrderDetailsTableDTO orderDetail : orderDetailsTableDTOs) {
        System.out.print(orderDetail.getOrderID()+" ");
        System.out.print(orderDetail.getItemCode()+" ");
        System.out.print(orderDetail.getDescription()+" ");
        System.out.print(orderDetail.getUnitPrice()+" ");
        System.out.print(orderDetail.getOrderQTY()+" ");
        System.out.print(orderDetail.getAmount()+" ");
        System.out.print(orderDetail.getDiscount()+" ");
        System.out.println(orderDetail.getAmount()+" ");
        }*/
        return orderDetailsTableDTOs;
    }

}
