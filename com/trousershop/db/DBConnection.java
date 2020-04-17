/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Sandun
 */
public class DBConnection {
    
    private static DBConnection dbConnection;
    private Connection conn;
    
    private DBConnection() throws SQLException, ClassNotFoundException {
        //Class.forName("com.mysql.jdbc.Driver"); depricated
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/trousershop", "root", "1234");
    }
    
    public static DBConnection getBConnection() throws SQLException, ClassNotFoundException{
        if (dbConnection==null){
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }
    
    public Connection getConnection(){
        return conn;
    }
    
    
    
}
