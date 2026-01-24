package com.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.MenuDTO;
import com.exam.entity.Menu;
import com.exam.entity.MessVendor;
import com.exam.repository.MenuRepository;
import com.exam.repository.MessVendorRepository;
import com.exam.service.MenuService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MessVendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Override
    public MenuDTO addMenu(Menu menu, Long vendorId) {

        MessVendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        menu.setVendor(vendor); // 🔗 mapping
        Menu saved = menuRepository.save(menu);

        return modelMapper.map(saved, MenuDTO.class);
    }

    @Override
    public List<MenuDTO> getAllMenu() {

        List<Menu> menus = menuRepository.findAll();
        List<MenuDTO> list = new ArrayList<>();

        for (Menu m : menus) {
            list.add(modelMapper.map(m, MenuDTO.class));
        }
        return list;
    }

    @Override
    public MenuDTO getMenuById(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        return modelMapper.map(menu, MenuDTO.class);
    }

    @Override
    public MenuDTO updateMenu(Long id, Menu menu) {

        Menu existing = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        existing.setItemName(menu.getItemName());
        existing.setType(menu.getType());
        existing.setPrice(menu.getPrice());

        Menu updated = menuRepository.save(existing);
        return modelMapper.map(updated, MenuDTO.class);
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}
