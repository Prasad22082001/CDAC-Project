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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private WorkerRepository workerRepository;
    private MessVendorRepository vendorRepository;
    private ModelMapper mapper;

    @Override
    public WorkerDTO addWorker(WorkerDTO dto) {

        // get vendor
        MessVendor vendor = vendorRepository.findById(dto.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        // map dto to entity
        Worker worker = mapper.map(dto, Worker.class);
        worker.setVendor(vendor);

        // save
        Worker saved = workerRepository.save(worker);

        return mapper.map(saved, WorkerDTO.class);
    }

    @Override
    public List<WorkerDTO> getAllWorkers() {

        return workerRepository.findAll()
                .stream()
                .map(w -> mapper.map(w, WorkerDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteWorker(Long id) {

        workerRepository.deleteById(id);
    }
}
