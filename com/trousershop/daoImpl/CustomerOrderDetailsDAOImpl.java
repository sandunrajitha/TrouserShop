/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.CustomerOrderDetailsDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.CustomerOrderDetailsDTO;
import com.trousershop.dto.CustomerOrdersDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public class CustomerOrderDetailsDAOImpl implements CustomerOrderDetailsDAO {

    @Override
    public boolean add(CustomerOrderDetailsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO customerorderdetails VALUES (?,?,?,?,?);";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getOrderID());
        stm.setObject(2, dto.getItemCode());
        stm.setObject(3, dto.getQty());
        stm.setObject(4, "" + dto.getUnitPrice());
        stm.setObject(5, "" + dto.getDiscount());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean update(CustomerOrderDetailsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE customerorderdetails SET itemCode = ?,"
                + "qty = ?, "
                + "unitPrice = ?, "
                + "discount = ? "
                + "WHERE orderId=?;";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getItemCode());
        stm.setObject(2, dto.getQty());
        stm.setObject(3, "" + dto.getUnitPrice());
        stm.setObject(4, "" + dto.getDiscount());
        stm.setObject(5, dto.getOrderID());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean delete(CustomerOrderDetailsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM customerorderdetails WHERE orderId = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getOrderID());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public CustomerOrderDetailsDTO search(CustomerOrderDetailsDTO dto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CustomerOrderDetailsDTO> getAll(CustomerOrdersDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM customerOrderdetails WHERE orderId='"+dto.getId()+"'";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<CustomerOrderDetailsDTO> allCustomerOrderDetails = null;
        
        while (rst.next()) {
            if (allCustomerOrderDetails == null) {
                allCustomerOrderDetails = new ArrayList<>();
            }
            allCustomerOrderDetails.add(new CustomerOrderDetailsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(5)
            ));
        }

        return allCustomerOrderDetails;
    }

}
