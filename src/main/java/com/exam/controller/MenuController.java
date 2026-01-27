package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.exam.dto.MenuDTO;
import com.exam.service.MenuService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/menu")
@CrossOrigin("*")
@AllArgsConstructor
public class MenuController {

    private MenuService menuService;

    // ✅ ADD MENU
    @PostMapping("/add")
    public MenuDTO addMenu(@RequestBody MenuDTO dto) {
        return menuService.addMenu(dto);
    }

    // ✅ GET MENU BY ID
    @GetMapping("/{id}")
    public MenuDTO getMenuById(@PathVariable Long id) {
        return menuService.getMenuById(id);
    }

    // ✅ GET ALL MENU
    @GetMapping("/all")
    public List<MenuDTO> getAllMenu() {
        return menuService.getAllMenu();
    }

    // ✅ DELETE MENU
    @DeleteMapping("/delete/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return "Menu deleted successfully";
    }
}
