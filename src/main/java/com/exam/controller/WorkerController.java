package com.exam.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.dto.WorkerDTO;
import com.exam.entity.Worker;
import com.exam.service.WorkerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/worker")
@CrossOrigin("*")
@AllArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    // ADD WORKER (vendor ke under)
    @PostMapping("/add/{vendorId}")
    public ResponseEntity<WorkerDTO> addWorker(
            @RequestBody Worker worker,
            @PathVariable Long vendorId) {

        return ResponseEntity.ok(workerService.addWorker(worker, vendorId));
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<List<WorkerDTO>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<WorkerDTO> getWorker(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getWorkerById(id));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<WorkerDTO> updateWorker(
            @PathVariable Long id,
            @RequestBody Worker worker) {

        return ResponseEntity.ok(workerService.updateWorker(id, worker));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return ResponseEntity.ok("Worker deleted successfully");
    }
}
