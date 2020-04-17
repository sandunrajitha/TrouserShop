/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.EmployeeDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface EmployeeDAO {
    
    public boolean add(EmployeeDTO dto) throws Exception;
    
    public boolean update(EmployeeDTO dto) throws Exception;
    
    public boolean delete(EmployeeDTO dto) throws Exception;
    
    public EmployeeDTO search(EmployeeDTO dto) throws Exception;
    
    public ArrayList<EmployeeDTO> getAll() throws Exception;
    
    public String getNextID() throws Exception;
}
