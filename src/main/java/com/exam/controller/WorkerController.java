package com.exam.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.exam.dto.WorkerDTO;
import com.exam.service.WorkerService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/worker")
@CrossOrigin("*")
@AllArgsConstructor
public class WorkerController {

    private WorkerService workerService;

    // ✅ ADD WORKER
    @PostMapping("/add")
    public WorkerDTO addWorker(@RequestBody WorkerDTO dto) {
        return workerService.addWorker(dto);
    }

    // ✅ GET ALL WORKERS
    @GetMapping("/all")
    public List<WorkerDTO> getAllWorkers() {
        return workerService.getAllWorkers();
    }

    // ✅ DELETE WORKER
    @DeleteMapping("/delete/{id}")
    public String deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return "Worker deleted successfully";
    }
}
