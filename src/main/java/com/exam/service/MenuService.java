package com.exam.service;

import java.util.List;
import com.exam.dto.MenuDTO;

public interface MenuService {

    MenuDTO addMenu(MenuDTO dto);

    MenuDTO getMenuById(Long id);

    List<MenuDTO> getAllMenu();

    void deleteMenu(Long id);
}
