package com.exam.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.exam.dto.WorkerDTO;
import com.exam.entity.MessVendor;
import com.exam.entity.Worker;
import com.exam.repository.MessVendorRepository;
import com.exam.repository.WorkerRepository;
import com.exam.service.WorkerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final MessVendorRepository vendorRepository;
    private final ModelMapper mapper;

    @Override
    public WorkerDTO addWorker(WorkerDTO dto, Long vendorId) {

        MessVendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Worker worker = mapper.map(dto, Worker.class);
        worker.setVendor(vendor);

        Worker saved = workerRepository.save(worker);

        WorkerDTO response = mapper.map(saved, WorkerDTO.class);
        response.setVendorId(vendor.getVendorId());

        return response;
    }

    @Override
    public List<WorkerDTO> getAllWorkers(Long userId, boolean isAdmin) {

        List<Worker> workers = isAdmin
                ? workerRepository.findAll()
                : workerRepository.findByVendorVendorId(userId);

        return workers.stream()
                .map(w -> {
                    WorkerDTO dto = mapper.map(w, WorkerDTO.class);
                    dto.setVendorId(w.getVendor().getVendorId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorker(Long workerId, Long userId, boolean isAdmin) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (!isAdmin &&
            !worker.getVendor().getVendorId().equals(userId)) {
            throw new RuntimeException("Access denied");
        }

        workerRepository.delete(worker);
    }
}
