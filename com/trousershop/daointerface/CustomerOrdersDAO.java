/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.CustomerOrdersDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface CustomerOrdersDAO {
    
    public boolean add(CustomerOrdersDTO dto) throws Exception;
    
    public boolean update(CustomerOrdersDTO dto) throws Exception;
    
    public boolean delete(CustomerOrdersDTO dto) throws Exception;
    
    public CustomerOrdersDTO search(CustomerOrdersDTO dto) throws Exception;
    
    public ArrayList<CustomerOrdersDTO> getAll() throws Exception;
    
    public String getNextID() throws Exception;
}
