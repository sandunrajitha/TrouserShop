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
public class CustomerPaymentsDTO implements SuperDTO {

    private String paymentID;
    private String orderID;
    private String date;
    private double payment;
    private double paidAmount;
    private String invoiceNo;
    private String method;
    private String chequeID;

    public CustomerPaymentsDTO() {
    }

    public CustomerPaymentsDTO(String paymentID, String orderID, String date, double payment, double paidAmount, String invoiceNo, String method, String chequeID) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.date = date;
        this.payment = payment;
        this.paidAmount = paidAmount;
        this.invoiceNo = invoiceNo;
        this.method = method;
        this.chequeID = chequeID;
    }

    /**
     * @return the paymentID
     */
    public String getPaymentID() {
        return paymentID;
    }

    /**
     * @param paymentID the paymentID to set
     */
    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
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
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the payment
     */
    public double getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(double payment) {
        this.payment = payment;
    }

    /**
     * @return the paidAmount
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * @param paidAmount the paidAmount to set
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the chequeID
     */
    public String getChequeID() {
        return chequeID;
    }

    /**
     * @param chequeID the chequeID to set
     */
    public void setChequeID(String chequeID) {
        this.chequeID = chequeID;
    }

}
