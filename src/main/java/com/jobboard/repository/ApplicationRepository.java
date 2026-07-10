package com.jobboard.repository;

import com.jobboard.model.Application;
import com.jobboard.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // all applications for a specific job
    List<Application> findByJobId(Long jobId);

    // all applications by a specific candidate
    List<Application> findByCandidateId(Long candidateId);

    // check if a candidate already applied to a job
    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);

    // all applications with a specific status
    List<Application> findByStatus(ApplicationStatus status);
}
