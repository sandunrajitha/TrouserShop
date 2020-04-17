/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.SupplierDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface SupplierDAO {
    
    public boolean add(SupplierDTO dto) throws Exception;
    
    public boolean update(SupplierDTO dto) throws Exception;
    
    public boolean delete(SupplierDTO dto) throws Exception;
    
    public SupplierDTO search(SupplierDTO dto) throws Exception;
    
    public ArrayList<SupplierDTO> getAll() throws Exception;
    
    public String getNextID() throws Exception;
    
}
