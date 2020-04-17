/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.SupplierDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.SupplierDTO;
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
public class SupplierDAOImpl implements SupplierDAO {

    @Override
    public boolean add(SupplierDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO suppliers VALUES (?,?,?,?,?,?,?);";
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
    public boolean update(SupplierDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE suppliers SET name = ?,address = ?, mobile = ?, phone = ?,addedDate = ?,notes = ? WHERE id=?;";
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
    public boolean delete(SupplierDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM suppliers WHERE id = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getId());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public SupplierDTO search(SupplierDTO dto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<SupplierDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM suppliers";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<SupplierDTO> allSuppliers = null;
        while (rst.next()) {
            if (allSuppliers == null) {
                allSuppliers = new ArrayList<>();
            }
            allSuppliers.add(new SupplierDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getInt(5),
                    rst.getString(6),
                    rst.getString(7)));
        }

        return allSuppliers;
    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT id FROM suppliers ORDER BY id DESC LIMIT 1";
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
