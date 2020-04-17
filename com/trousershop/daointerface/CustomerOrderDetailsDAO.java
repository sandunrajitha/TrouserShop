/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.CustomerOrderDetailsDTO;
import com.trousershop.dto.CustomerOrdersDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface CustomerOrderDetailsDAO {
    
    public boolean add(CustomerOrderDetailsDTO dto) throws Exception;
    
    public boolean update(CustomerOrderDetailsDTO dto) throws Exception;
    
    public boolean delete(CustomerOrderDetailsDTO dto) throws Exception;
    
    public CustomerOrderDetailsDTO search(CustomerOrderDetailsDTO dto) throws Exception;
    
    public ArrayList<CustomerOrderDetailsDTO> getAll(CustomerOrdersDTO dto)throws Exception;
    
}
