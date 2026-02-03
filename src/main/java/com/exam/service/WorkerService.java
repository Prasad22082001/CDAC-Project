package com.exam.service;

import java.util.List;
import com.exam.dto.WorkerDTO;

public interface WorkerService {

    WorkerDTO addWorker(WorkerDTO dto, Long vendorId);

    List<WorkerDTO> getAllWorkers(Long userId, boolean isAdmin);

    void deleteWorker(Long workerId, Long userId, boolean isAdmin);
}
