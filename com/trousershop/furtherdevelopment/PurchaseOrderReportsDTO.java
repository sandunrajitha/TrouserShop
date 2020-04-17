/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.dto;

/**
 *
 * @author Sandun
 */
public class PurchaseOrderReportsDTO implements SuperDTO{
    private String id;
    private String printedDate;
    private String orderID;

    public PurchaseOrderReportsDTO() {
    }

    public PurchaseOrderReportsDTO(String id, String printedDate, String orderID) {
        this.id = id;
        this.printedDate = printedDate;
        this.orderID = orderID;
    }

    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the printedDate
     */
    public String getPrintedDate() {
        return printedDate;
    }

    /**
     * @param printedDate the printedDate to set
     */
    public void setPrintedDate(String printedDate) {
        this.printedDate = printedDate;
    }

    /**
     * @return the orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
    
    
}
