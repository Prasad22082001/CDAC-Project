package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.WorkerDTO;
import com.exam.security.UserPrincipal;
import com.exam.service.WorkerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/worker")
@CrossOrigin("*")
@AllArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    // ➕ ADD WORKER (VENDOR ONLY)
    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping("/add")
    public ResponseEntity<WorkerDTO> addWorker(
            @Valid @RequestBody WorkerDTO dto,
            @AuthenticationPrincipal UserPrincipal principal) {

        return ResponseEntity.ok(
                workerService.addWorker(dto, principal.getUserId())
        );
    }

    // 👀 GET WORKERS
    // ADMIN → all
    // VENDOR → own only
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @GetMapping("/all")
    public ResponseEntity<List<WorkerDTO>> getAllWorkers(
            @AuthenticationPrincipal UserPrincipal principal) {

        boolean isAdmin = principal.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return ResponseEntity.ok(
                workerService.getAllWorkers(principal.getUserId(), isAdmin)
        );
    }

    // ❌ DELETE WORKER
    @PreAuthorize("hasAnyRole('ADMIN','VENDOR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorker(
            @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal principal) {

        boolean isAdmin = principal.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        workerService.deleteWorker(id, principal.getUserId(), isAdmin);
        return ResponseEntity.ok("Worker deleted successfully");
    }
}
