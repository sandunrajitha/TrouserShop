/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.CustomerDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface CustomerDAO {
    public boolean add(CustomerDTO dto) throws Exception;
    
    public boolean update(CustomerDTO dto) throws Exception;
    
    public boolean delete(CustomerDTO dto) throws Exception;
    
    public CustomerDTO search(String id) throws Exception;
    
    public ArrayList<CustomerDTO> getAll()throws Exception;
    
    public String getNextID() throws Exception;
}
