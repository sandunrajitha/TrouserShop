/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.CustomerDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.CustomerDTO;
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
public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(CustomerDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO customers VALUES (?,?,?,?,?,?,?);";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getId());
        stm.setObject(2, dto.getName());
        stm.setObject(3, dto.getAddress());
        stm.setObject(4, "" + dto.getMobile());
        stm.setObject(5, "" + dto.getPhone());
        stm.setObject(6, dto.getAddedDate());
        stm.setObject(7, dto.getNotes());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean update(CustomerDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE customers SET name = ?,address = ?, mobile = ?, phone = ?,addedDate = ?,notes = ? WHERE id=?;";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getName());
        stm.setObject(2, dto.getAddress());
        stm.setObject(3, "" + dto.getMobile());
        stm.setObject(4, "" + dto.getPhone());
        stm.setObject(5, dto.getAddedDate());
        stm.setObject(6, dto.getNotes());
        stm.setObject(7, dto.getId());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean delete(CustomerDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM customers WHERE id = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getId());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public CustomerDTO search(String id) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM customers WHERE id = '"+id+"'";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        rst.next();
        CustomerDTO customer = new CustomerDTO(
                rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getString(7));
        return customer;
    }

    @Override
    public ArrayList<CustomerDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM customers";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<CustomerDTO> allCustomers = null;
        while (rst.next()) {
            if (allCustomers == null) {
                allCustomers = new ArrayList<>();
            }
            allCustomers.add(new CustomerDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getString(7)
            ));
        }

        return allCustomers;
    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT id FROM customers ORDER BY id DESC LIMIT 1";
        ResultSet rst = stm.executeQuery(SQL);
        String lastID, nextID = null;
        while (rst.next()) {
            lastID = rst.getString(1);
            int temp = Integer.parseInt(lastID.substring(2, 6)) + 1;
            DecimalFormat format = new DecimalFormat("CS" + "0000");
            nextID = format.format(temp);
        }
        //System.out.println(nextID);
        return nextID;

    }

}
