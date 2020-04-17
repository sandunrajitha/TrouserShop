/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.ItemsDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.ItemsDTO;
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
public class ItemsDAOImpl implements ItemsDAO {

    @Override
    public boolean add(ItemsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO items VALUES (?,?,?,?,?,?,?);";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getCode());
        stm.setObject(2, dto.getDescription());
        stm.setObject(3, "" + dto.getUnitPrice());
        stm.setObject(4, "" + dto.getDiscount());
        stm.setObject(5, "" + dto.getQtyOnHand());
        stm.setObject(6, dto.getAddedDate());
        stm.setObject(7, dto.getNotes());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean update(ItemsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE items SET "
                + "description = ?, "
                + "unitPrice = ?, "
                + "discount = ?,"
                + "qtyOnHand = ?,"
                + "addedDate = ?, "
                + "notes = ? "
                + "WHERE code=?;";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getDescription());
        stm.setObject(2, dto.getUnitPrice());
        stm.setObject(3, "" + dto.getDiscount());
        stm.setObject(4, "" + dto.getQtyOnHand());
        stm.setObject(5, dto.getAddedDate());
        stm.setObject(6, dto.getNotes());
        stm.setObject(7, dto.getCode());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean delete(ItemsDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM items WHERE code = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getCode());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public ItemsDTO search(String code) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM items WHERE code ='" + code + "'";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);
        
        ItemsDTO item = null;
        
        while (rst.next()) {
            item = new ItemsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getString(7));
        }
        return item;
    }

    @Override
    public ArrayList<ItemsDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM Items";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<ItemsDTO> allItems = null;
        while (rst.next()) {
            if (allItems == null) {
                allItems = new ArrayList<>();
            }
            allItems.add(new ItemsDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getString(7)));
        }

        return allItems;
    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT code FROM items ORDER BY code DESC LIMIT 1";
        ResultSet rst = stm.executeQuery(SQL);
        String lastID, nextID = null;

        while (rst.next()) {
            lastID = rst.getString(1);
            int temp = Integer.parseInt(lastID.substring(2, 6)) + 1;
            DecimalFormat format = new DecimalFormat("IT" + "0000");
            nextID = format.format(temp);
        }

        System.out.println(nextID);
        return nextID;
    }
}
