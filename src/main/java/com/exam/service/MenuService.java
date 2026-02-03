package com.exam.service;

import java.util.List;
import com.exam.dto.MenuDTO;

public interface MenuService {

    MenuDTO addMenu(MenuDTO dto, Long loggedInVendorId);

    MenuDTO getMenuById(Long id);

    List<MenuDTO> getAllMenu();

    void deleteMenu(Long id, Long loggedInVendorId, boolean isAdmin);
    
    List<MenuDTO> getMenuForStudent(Long studentId);

}
