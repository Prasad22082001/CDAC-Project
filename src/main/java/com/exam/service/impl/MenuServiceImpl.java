package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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

    private MenuRepository menuRepository;
    private MessVendorRepository vendorRepository;
    private ModelMapper mapper;

    @Override
    public MenuDTO addMenu(MenuDTO dto) {

        // get vendor
        MessVendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // map dto to entity
        Menu menu = mapper.map(dto, Menu.class);
        menu.setVendor(vendor);

        // save menu
        Menu saved = menuRepository.save(menu);

        // return dto
        return mapper.map(saved, MenuDTO.class);
    }

    @Override
    public MenuDTO getMenuById(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        return mapper.map(menu, MenuDTO.class);
    }

    @Override
    public List<MenuDTO> getAllMenu() {

        return menuRepository.findAll()
                .stream()
                .map(menu -> mapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMenu(Long id) {

        menuRepository.deleteById(id);
    }
}
