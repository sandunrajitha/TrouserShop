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
public class IssuedChequeStatusDTO implements SuperDTO{
    private String id;
    private String chequeID;
    private String status;

    public IssuedChequeStatusDTO() {
    }

    public IssuedChequeStatusDTO(String id, String chequeID, String status) {
        this.id = id;
        this.chequeID = chequeID;
        this.status = status;
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

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
