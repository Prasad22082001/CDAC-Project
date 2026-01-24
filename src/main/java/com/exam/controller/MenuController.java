package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MenuDTO;
import com.exam.entity.Menu;
import com.exam.service.MenuService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/menu")
@CrossOrigin("*")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // ADD MENU (vendor ke under)
    @PostMapping("/add/{vendorId}")
    public ResponseEntity<MenuDTO> addMenu(
            @RequestBody Menu menu,
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(menuService.addMenu(menu, vendorId));
    }

    // GET ALL MENU
    @GetMapping("/all")
    public ResponseEntity<List<MenuDTO>> getAllMenu() {
        return ResponseEntity.ok(menuService.getAllMenu());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenu(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    // UPDATE MENU
    @PutMapping("/update/{id}")
    public ResponseEntity<MenuDTO> updateMenu(
            @PathVariable Long id,
            @RequestBody Menu menu) {

        return ResponseEntity.ok(menuService.updateMenu(id, menu));
    }

    // DELETE MENU
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok("Menu item deleted successfully");
    }
}
