/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trousershop.daointerface;

import com.trousershop.dto.UserDTO;
import java.util.ArrayList;

/**
 *
 * @author Sandun
 */
public interface UserDAO {
    
    public boolean add(UserDTO dto) throws Exception;
    
    public boolean update(UserDTO dto) throws Exception;
    
    public boolean delete(UserDTO dto) throws Exception;
    
    public UserDTO search(UserDTO dto) throws Exception;
    
    public ArrayList<UserDTO> getAll() throws Exception;
    
}
