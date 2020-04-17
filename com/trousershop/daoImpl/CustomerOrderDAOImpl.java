/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.CustomerOrdersDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.CustomerOrdersDTO;
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
public class CustomerOrderDAOImpl implements CustomerOrdersDAO {

    @Override
    public boolean add(CustomerOrdersDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO customerorders VALUES (?,?,?,?,?,?);";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getId());
        stm.setObject(2, dto.getCustomerID());
        stm.setObject(3, dto.getAddedDate());
        stm.setObject(4, dto.getDueDate());
        stm.setObject(5, dto.getDiscount());
        stm.setObject(6, dto.getAmount());
        int successful = stm.executeUpdate();

        return (successful > 0);

    }

    @Override
    public boolean update(CustomerOrdersDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE customerorders SET "
                + "customerId = ?,"
                + "addedDate = ?, "
                + "dueDate = ?, "
                + "discount = ?,"
                + "amount = ? "
                + "WHERE id=?;";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getCustomerID());
        stm.setObject(2, dto.getAddedDate());
        stm.setObject(3, "" + dto.getDueDate());
        stm.setObject(4, "" + dto.getDiscount());
        stm.setObject(5, dto.getAmount());
        stm.setObject(6, dto.getId());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean delete(CustomerOrdersDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM customerorders WHERE id = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getId());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public CustomerOrdersDTO search(CustomerOrdersDTO dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<CustomerOrdersDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM customerorders";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<CustomerOrdersDTO> allCustomerOrders = null;
        while (rst.next()) {
            if (allCustomerOrders == null) {
                allCustomerOrders = new ArrayList<>();
            }
            allCustomerOrders.add(new CustomerOrdersDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getDouble(6)));
        }
        /*for (CustomerOrdersDTO customerOrder : allCustomerOrders) {
        System.out.println(customerOrder.getAmount()+" "+customerOrder.getDiscount());
        }*/
        return allCustomerOrders;

    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT id FROM customerorders ORDER BY id DESC LIMIT 1";
        ResultSet rst = stm.executeQuery(SQL);
        String lastID, nextID = null;

        while (rst.next()) {
            lastID = rst.getString(1);
            int temp = Integer.parseInt(lastID.substring(2, 6)) + 1;
            DecimalFormat format = new DecimalFormat("CO" + "0000");
            nextID = format.format(temp);
        }

        System.out.println(nextID);
        return nextID;
    }

}
