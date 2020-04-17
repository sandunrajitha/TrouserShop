/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.ItemsDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface ItemsDAO {
    
    public boolean add(ItemsDTO dto) throws Exception;
    
    public boolean update(ItemsDTO dto) throws Exception;
    
    public boolean delete(ItemsDTO dto) throws Exception;
    
    public ItemsDTO search(String code) throws Exception;
    
    public ArrayList<ItemsDTO> getAll() throws Exception;
    
    public String getNextID() throws Exception;
}
