/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.SupplierPaymentsDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.SupplierPaymentsDTO;
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
public class SupplierPaymentsDAOImpl implements SupplierPaymentsDAO {

    @Override
    public boolean add(SupplierPaymentsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO supplierpayments VALUES (?,?,?,?,?,?,?,'');";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getPaymentID());
        stm.setObject(2, dto.getOrderID());
        stm.setObject(3, dto.getDate());
        stm.setObject(4, "" + dto.getPayment());
        stm.setObject(5, "" + dto.getPaidAmount());
        stm.setObject(6, dto.getInvoiceNo());
        stm.setObject(7, dto.getMethod());
        
        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean update(SupplierPaymentsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE supplierpayments SET "
                + "orderId = ?,"
                + "date = ?, "
                + "payment = ?, "
                + "paidAmount = ?,"
                + "invoiceNo = ?,"
                + "method = ? "
                + "WHERE paymentId=?;";
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
    public boolean delete(SupplierPaymentsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM supplierpayments WHERE paymentId = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getPaymentID());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public SupplierPaymentsDTO search(SupplierPaymentsDTO dto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<SupplierPaymentsDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT * FROM supplierpayments";
        ResultSet rst = stm.executeQuery(SQL);
        ArrayList<SupplierPaymentsDTO> allSupplierPayments = null;
        while (rst.next()) {
            if (allSupplierPayments == null) {
                allSupplierPayments = new ArrayList<>();
            }
            allSupplierPayments.add(new SupplierPaymentsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDouble(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8)));
        }
        return allSupplierPayments;
    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT paymentId FROM supplierpayments ORDER BY paymentId DESC LIMIT 1";
        ResultSet rst = stm.executeQuery(SQL);
        String lastID, nextID = null;
        while (rst.next()) {
            lastID = rst.getString(1);
            int temp = Integer.parseInt(lastID.substring(2, 6)) + 1;
            DecimalFormat format = new DecimalFormat("SP" + "0000");
            nextID = format.format(temp);
        }
        //System.out.println(nextID);
        return nextID;
    }

}
