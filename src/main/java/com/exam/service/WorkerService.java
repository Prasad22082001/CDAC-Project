package com.exam.service;

import java.util.List;
import com.exam.dto.WorkerDTO;
import com.exam.entity.Worker;

public interface WorkerService {

    WorkerDTO addWorker(Worker worker, Long vendorId);

    List<WorkerDTO> getAllWorkers();

    WorkerDTO getWorkerById(Long id);

    WorkerDTO updateWorker(Long id, Worker worker);

    void deleteWorker(Long id);
}
