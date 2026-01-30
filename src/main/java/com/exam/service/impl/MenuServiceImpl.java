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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MessVendorRepository vendorRepository;
    private final ModelMapper mapper;

    @Override
    public MenuDTO addMenu(MenuDTO dto, Long loggedInVendorId) {

        if (!dto.getVendorId().equals(loggedInVendorId)) {
            throw new RuntimeException("Vendor can add menu only for own mess");
        }

        MessVendor vendor = vendorRepository.findById(loggedInVendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Menu menu = mapper.map(dto, Menu.class);
        menu.setVendor(vendor);

        Menu saved = menuRepository.save(menu);

        // 🔧 FIX: vendorId set in response
        MenuDTO response = mapper.map(saved, MenuDTO.class);
        response.setVendorId(vendor.getVendorId());

        return response;
    }

    @Override
    public MenuDTO getMenuById(Long id) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        MenuDTO dto = mapper.map(menu, MenuDTO.class);
        dto.setVendorId(menu.getVendor().getVendorId()); // 🔧 FIX

        return dto;
    }

    @Override
    public List<MenuDTO> getAllMenu() {

        return menuRepository.findAll()
                .stream()
                .map(menu -> {
                    MenuDTO dto = mapper.map(menu, MenuDTO.class);
                    dto.setVendorId(menu.getVendor().getVendorId()); // 🔧 FIX
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMenu(Long id, Long loggedInVendorId, boolean isAdmin) {

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found"));

        if (!isAdmin && !menu.getVendor().getVendorId().equals(loggedInVendorId)) {
            throw new RuntimeException("Access denied");
        }

        menuRepository.delete(menu);
    }
}
