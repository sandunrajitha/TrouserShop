/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daoImpl;

import com.trousershop.daointerface.EmployeeDAO;
import com.trousershop.db.DBConnection;
import com.trousershop.dto.EmployeeDTO;
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
public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean add(EmployeeDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "INSERT INTO employees VALUES (?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getId());
        stm.setObject(2, dto.getName());
        stm.setObject(3, dto.getNic());
        stm.setObject(4, dto.getDob());
        stm.setObject(5, dto.getAddress());
        stm.setObject(6, dto.getSalary());
        stm.setObject(7, "" + dto.getMobile());
        stm.setObject(8, "" + dto.getPhone());
        stm.setObject(9, dto.getJoinedDate());
        stm.setObject(10, dto.getNotes());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean update(EmployeeDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "UPDATE employees SET "
                        + "name = ?,"
                        + "nic = ?,"
                        + "dob = ?, "
                        + "address = ?, "
                        + "salary = ?,"
                        + "mobile = ?,"
                        + "phone = ?, "
                        + "joinedDate = ?, "
                        + "notes = ? "
                        + "WHERE id=?;";
        PreparedStatement stm = conn.prepareStatement(SQL);

        stm.setObject(1, dto.getName());
        stm.setObject(2, dto.getNic());
        stm.setObject(3, dto.getDob());
        stm.setObject(4, dto.getAddress());
        stm.setObject(5, ""+dto.getSalary());
        stm.setObject(6, ""+dto.getMobile());
        stm.setObject(7, ""+dto.getPhone());
        stm.setObject(8, dto.getJoinedDate());
        stm.setObject(9, dto.getNotes());
        stm.setObject(10, dto.getId());

        int successful = stm.executeUpdate();
        return (successful > 0);
    }

    @Override
    public boolean delete(EmployeeDTO dto) throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "DELETE FROM employees WHERE id = ? ";
        PreparedStatement stm = conn.prepareStatement(SQL);
        stm.setObject(1, dto.getId());
        int deleted = stm.executeUpdate();
        return (deleted > 0);
    }

    @Override
    public EmployeeDTO search(EmployeeDTO dto) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<EmployeeDTO> getAll() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        String SQL = "SELECT * FROM employees";
        Statement stm = conn.createStatement();
        ResultSet rst = stm.executeQuery(SQL);

        ArrayList<EmployeeDTO> allEmployees = null;
        while (rst.next()) {
            if (allEmployees == null) {
                allEmployees = new ArrayList<>();
            }
            allEmployees.add(new EmployeeDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getDouble(6),
                    rst.getInt(7),
                    rst.getInt(8),
                    rst.getString(9),
                    rst.getString(10)));
        }

        return allEmployees;
    }

    @Override
    public String getNextID() throws Exception {
        Connection conn = DBConnection.getBConnection().getConnection();
        Statement stm = conn.createStatement();
        String SQL = "SELECT id FROM employees ORDER BY id DESC LIMIT 1";
        ResultSet rst = stm.executeQuery(SQL);
        String lastID, nextID = null;
        while (rst.next()) {
            lastID = rst.getString(1);
            int temp = Integer.parseInt(lastID.substring(2, 6)) + 1;
            DecimalFormat format = new DecimalFormat("EM" + "0000");
            nextID = format.format(temp);
        }
        //System.out.println(nextID);
        return nextID;
    }

}
