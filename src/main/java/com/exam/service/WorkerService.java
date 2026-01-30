package com.exam.service;

import java.util.List;
import com.exam.dto.WorkerDTO;

public interface WorkerService {

    WorkerDTO addWorker(WorkerDTO dto, Long loggedInVendorId);

    List<WorkerDTO> getAllWorkers(Long loggedInVendorId, boolean isAdmin);

    void deleteWorker(Long workerId, Long loggedInVendorId, boolean isAdmin);
}
