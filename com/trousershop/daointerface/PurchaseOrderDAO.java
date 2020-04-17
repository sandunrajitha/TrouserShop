/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.PurchaseOrderDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface PurchaseOrderDAO {
    
    public boolean add(PurchaseOrderDTO dto) throws Exception;
    
    public boolean update(PurchaseOrderDTO dto) throws Exception;
    
    public boolean delete(PurchaseOrderDTO dto) throws Exception;
    
    public PurchaseOrderDTO search(PurchaseOrderDTO dto) throws Exception;
    
    public ArrayList<PurchaseOrderDTO> getAll() throws Exception;
    
    public String getNextID() throws Exception;
    
}
