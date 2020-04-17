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
public class ReceivedChequeDTO implements SuperDTO{
    private String id;
    private String orderID;
    private String number;
    private String bank;
    private String issuedDate;
    private String realizationDate;
    private double amount;

    public ReceivedChequeDTO() {
    }

    public ReceivedChequeDTO(String id, String orderID, String number, String bank, String issuedDate, String realizationDate, double amount) {
        this.id = id;
        this.orderID = orderID;
        this.number = number;
        this.bank = bank;
        this.issuedDate = issuedDate;
        this.realizationDate = realizationDate;
        this.amount = amount;
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

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the bank
     */
    public String getBank() {
        return bank;
    }

    /**
     * @param bank the bank to set
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * @return the issuedDate
     */
    public String getIssuedDate() {
        return issuedDate;
    }

    /**
     * @param issuedDate the issuedDate to set
     */
    public void setIssuedDate(String issuedDate) {
        this.issuedDate = issuedDate;
    }

    /**
     * @return the realizationDate
     */
    public String getRealizationDate() {
        return realizationDate;
    }

    /**
     * @param realizationDate the realizationDate to set
     */
    public void setRealizationDate(String realizationDate) {
        this.realizationDate = realizationDate;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
}
