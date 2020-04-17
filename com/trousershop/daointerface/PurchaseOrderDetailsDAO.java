/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.PurchaseOrderDetailsDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface PurchaseOrderDetailsDAO {
    
    public boolean add(PurchaseOrderDetailsDTO dto) throws Exception;
    
    public boolean update(PurchaseOrderDetailsDTO dto) throws Exception;
    
    public boolean delete(PurchaseOrderDetailsDTO dto) throws Exception;
    
    public PurchaseOrderDetailsDTO search(PurchaseOrderDetailsDTO dto) throws Exception;
    
    public ArrayList<PurchaseOrderDetailsDTO> getAll() throws Exception;
        
}
