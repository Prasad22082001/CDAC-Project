package com.exam.service;

import java.util.List;
import com.exam.dto.WorkerDTO;

public interface WorkerService {

    WorkerDTO addWorker(WorkerDTO dto);

    List<WorkerDTO> getAllWorkers();

    void deleteWorker(Long id);
}
