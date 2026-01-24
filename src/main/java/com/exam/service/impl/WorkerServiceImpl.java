package com.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.WorkerDTO;
import com.exam.entity.MessVendor;
import com.exam.entity.Worker;
import com.exam.repository.MessVendorRepository;
import com.exam.repository.WorkerRepository;
import com.exam.service.WorkerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final MessVendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Override
    public WorkerDTO addWorker(Worker worker, Long vendorId) {

        MessVendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        worker.setVendor(vendor); // 🔗 mapping set
        Worker saved = workerRepository.save(worker);

        return modelMapper.map(saved, WorkerDTO.class);
    }

    @Override
    public List<WorkerDTO> getAllWorkers() {
        List<Worker> workers = workerRepository.findAll();
        List<WorkerDTO> list = new ArrayList<>();

        for (Worker w : workers) {
            list.add(modelMapper.map(w, WorkerDTO.class));
        }
        return list;
    }

    @Override
    public WorkerDTO getWorkerById(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        return modelMapper.map(worker, WorkerDTO.class);
    }

    @Override
    public WorkerDTO updateWorker(Long id, Worker worker) {

        Worker existing = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        existing.setName(worker.getName());
        existing.setRole(worker.getRole());
        existing.setContact(worker.getContact());

        Worker updated = workerRepository.save(existing);
        return modelMapper.map(updated, WorkerDTO.class);
    }

    @Override
    public void deleteWorker(Long id) {
        workerRepository.deleteById(id);
    }
}
