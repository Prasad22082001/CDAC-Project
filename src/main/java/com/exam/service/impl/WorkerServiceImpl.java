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
    public WorkerDTO addWorker(WorkerDTO dto, Long loggedInVendorId) {

        // vendor can add only to own mess
        if (!dto.getVendorId().equals(loggedInVendorId)) {
            throw new RuntimeException("Vendor can add worker only to own mess");
        }

        MessVendor vendor = vendorRepository.findById(loggedInVendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Worker worker = mapper.map(dto, Worker.class);
        worker.setVendor(vendor);

        Worker saved = workerRepository.save(worker);
        return mapper.map(saved, WorkerDTO.class);
    }

    @Override
    public List<WorkerDTO> getAllWorkers(Long loggedInVendorId, boolean isAdmin) {

        List<Worker> workers;

        if (isAdmin) {
            workers = workerRepository.findAll();
        } else {
            workers = workerRepository.findByVendorVendorId(loggedInVendorId);
        }

        return workers.stream()
                .map(w -> mapper.map(w, WorkerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorker(Long workerId, Long loggedInVendorId, boolean isAdmin) {

        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (!isAdmin &&
            !worker.getVendor().getVendorId().equals(loggedInVendorId)) {
            throw new RuntimeException("Access denied");
        }

        workerRepository.delete(worker);
    }
}
