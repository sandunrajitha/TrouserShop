/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.CustomerPaymentsDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.CustomerPaymentsDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public class CustomerPaymentsDAOImpl implements CustomerPaymentsDAO {

    @Override
    public boolean add(CustomerPaymentsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO customerpayments VALUES (?,?,?,?,?,?,?,?,'');";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getPaymentID());
        stm.setObject(2, dto.getOrderID());
        stm.setObject(4, dto.getDate());
        stm.setObject(5, dto.getPayment());
        stm.setObject(5, dto.getPaidAmount());
        stm.setObject(6, dto.getInvoiceNo());
        stm.setObject(6, dto.getMethod());
        stm.setObject(6, dto.getChequeID());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean update(CustomerPaymentsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE customerpayments SET orderId = ?,date = ?, payment = ?, paidAmount = ?,invoiceNo = ?,method = ? WHERE paymentId=?;";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getOrderID());
        stm.setObject(2, dto.getDate());
        stm.setObject(3, "" + dto.getPayment());
        stm.setObject(4, "" + dto.getPaidAmount());
        stm.setObject(5, dto.getInvoiceNo());
        stm.setObject(6, dto.getMethod());
        stm.setObject(7, dto.getPaymentID());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean delete(CustomerPaymentsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM customerpayments WHERE paymentId = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getPaymentID());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public CustomerPaymentsDTO search(CustomerPaymentsDTO dto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CustomerPaymentsDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM customerpayments";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<CustomerPaymentsDTO> allCustomerPayments = null;
        while (rst.next()) {
            if (allCustomerPayments == null) {
                allCustomerPayments = new ArrayList<>();
            }
            allCustomerPayments.add(new CustomerPaymentsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)));
        }

        return allCustomerPayments;
    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT paymentId FROM customerpayments ORDER BY paymentId DESC LIMIT 1";
        ResultSet rst = stm.executeQuery(SQL);
        String lastID, nextID = null;
        while (rst.next()) {
            lastID = rst.getString(1);
            int temp = Integer.parseInt(lastID.substring(2, 6)) + 1;
            DecimalFormat format = new DecimalFormat("CP" + "0000");
            nextID = format.format(temp);
        }
        //System.out.println(nextID);
        return nextID;
    }

}
