/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.CustomerPaymentsDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface CustomerPaymentsDAO {
    
    public boolean add(CustomerPaymentsDTO dto) throws Exception;
    
    public boolean update(CustomerPaymentsDTO dto) throws Exception;
    
    public boolean delete(CustomerPaymentsDTO dto) throws Exception;
    
    public CustomerPaymentsDTO search(CustomerPaymentsDTO dto) throws Exception;
    
    public ArrayList<CustomerPaymentsDTO> getAll() throws Exception;
    
    public String getNextID() throws Exception;
    
}
