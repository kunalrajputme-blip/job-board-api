package com.jobboard.repository;

import com.jobboard.model.EmploymentType;
import com.jobboard.model.Job;
import com.jobboard.model.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // find all jobs for a specific company
    List<Job> findByCompanyId(Long companyId);

    // find all jobs with a specific status
    List<Job> findByStatus(JobStatus status);

    // find all jobs by location containing a string (case-insensitive)
    List<Job> findByLocationContainingIgnoreCase(String location);

    // find all jobs with salary in range
    List<Job> findByMinSalaryGreaterThanEqual(Double minSalary);

    // find all jobs by employment type AND status
    List<Job> findByEmploymentTypeAndStatus(EmploymentType type, JobStatus status);

    // find all OPEN jobs for a company
    @Query("SELECT j FROM Job j WHERE j.company.id = :companyId AND j.status = 'OPEN'")
    List<Job> findOpenJobsByCompany(@Param("companyId") Long companyId);

    // find jobs that have a specific tag
    @Query("SELECT j FROM Job j JOIN j.tags t WHERE t.name = :tagName")
    List<Job> findJobsByTagName(@Param("tagName") String tagName);

    // find jobs within salary range
    @Query("SELECT j FROM Job j WHERE j.minSalary >= :min AND j.maxSalary <= :max AND j.status = 'OPEN'")
    List<Job> findJobsInSalaryRange(@Param("min") Double min, @Param("max") Double max);
}
