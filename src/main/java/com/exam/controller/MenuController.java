package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.MenuDTO;
import com.exam.security.UserPrincipal;
import com.exam.service.MenuService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/menu")
@CrossOrigin("*")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // ➕ ADD MENU (VENDOR ONLY)
    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping("/add")
    public ResponseEntity<MenuDTO> addMenu(
            @Valid @RequestBody MenuDTO dto,
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                menuService.addMenu(dto, principal.getUserId())
        );
    }

    // 👀 GET MENU BY ID
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR','STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    // 👀 ADMIN / VENDOR → ALL MENUS
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @GetMapping("/all")
    public ResponseEntity<List<MenuDTO>> getAllMenu() {
        return ResponseEntity.ok(menuService.getAllMenu());
    }

    // 🎓 STUDENT → ONLY SELECTED VENDOR MENU ✅🔥
    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/student")
    public ResponseEntity<List<MenuDTO>> getStudentMenu(
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                menuService.getMenuForStudent(principal.getUserId())
        );
    }

    // ❌ DELETE MENU (ADMIN OR OWNER VENDOR)
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMenu(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        boolean isAdmin = principal.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        menuService.deleteMenu(id, principal.getUserId(), isAdmin);
        return ResponseEntity.ok("Menu deleted successfully");
    }
}
