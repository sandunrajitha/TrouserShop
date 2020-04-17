/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.PurchaseOrderDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.PurchaseOrderDTO;
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
public class PurchaseOrderDAOImpl implements PurchaseOrderDAO {

    @Override
    public boolean add(PurchaseOrderDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO purchaseorders VALUES (?,?,?,?,?,?);";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getId());
        stm.setObject(2, dto.getSupplierID());
        stm.setObject(3, dto.getAddedDate());
        stm.setObject(4, dto.getDueDate());
        stm.setObject(5, dto.getAmount());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean update(PurchaseOrderDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE purchaseorders SET "
                + "supplierId = ?,"
                + "addedDate = ?, "
                + "dueDate = ?, "
                + "amount = ? "
                + "WHERE id=?;";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getSupplierID());
        stm.setObject(2, dto.getAddedDate());
        stm.setObject(3, "" + dto.getDueDate());
        stm.setObject(4, dto.getAmount());
        stm.setObject(5, dto.getId());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean delete(PurchaseOrderDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM purchaseorders WHERE id = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getId());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public PurchaseOrderDTO search(PurchaseOrderDTO dto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<PurchaseOrderDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT * FROM purchaseorders";
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<PurchaseOrderDTO> purchaseOrders = null;
        while (rst.next()) {
            if (purchaseOrders == null) {
                purchaseOrders = new ArrayList<>();
            }
            purchaseOrders.add(new PurchaseOrderDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5)));
        }
        return purchaseOrders;
    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT id FROM purchaseorders ORDER BY id DESC LIMIT 1";
        ResultSet rst = stm.executeQuery(SQL);
        String lastID, nextID = null;

        while (rst.next()) {
            lastID = rst.getString(1);
            int temp = Integer.parseInt(lastID.substring(2, 6)) + 1;
            DecimalFormat format = new DecimalFormat("PO" + "0000");
            nextID = format.format(temp);
        }

        System.out.println(nextID);
        return nextID;
    }
}
