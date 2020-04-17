/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.SupplierPaymentsDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface SupplierPaymentsDAO {
    
    public boolean add(SupplierPaymentsDTO dto) throws Exception;
    
    public boolean update(SupplierPaymentsDTO dto) throws Exception;
    
    public boolean delete(SupplierPaymentsDTO dto) throws Exception;
    
    public SupplierPaymentsDTO search(SupplierPaymentsDTO dto) throws Exception;
    
    public ArrayList<SupplierPaymentsDTO> getAll() throws Exception;
    
    public String getNextID() throws Exception;
}
