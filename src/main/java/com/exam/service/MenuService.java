package com.exam.service;

import java.util.List;
import com.exam.dto.MenuDTO;
import com.exam.entity.Menu;

public interface MenuService {

    MenuDTO addMenu(Menu menu, Long vendorId);

    List<MenuDTO> getAllMenu();

    MenuDTO getMenuById(Long id);

    MenuDTO updateMenu(Long id, Menu menu);

    void deleteMenu(Long id);
}
