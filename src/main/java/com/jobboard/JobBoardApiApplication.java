package com.jobboard;

import com.jobboard.model.*;
import com.jobboard.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class JobBoardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobBoardApiApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner testRepositories(
            ApplicationRepository applicationRepo,
            CompanyRepository companyRepo,
            JobRepository jobRepo,
            TagRepository tagRepo) {
        return args -> {
//             Create a company
            Company company = new Company(LocalDate.of(2010, 1, 1), "Technology", "TechCorp",
                    "https://techcorp.com");
            Company saved = companyRepo.save(company);
            System.out.println("Saved company ID: " + saved.getId());

            // Create tags
//            Tag remoteTag = tagRepo.save(new Tag("Remote"));
//            Tag fullTimeTag = tagRepo.save(new Tag("Full-Time"));

            // Create a job and add tags
            Job job = new Job("Backend Developer", "Spring Boot role",
                    "Bangalore", 800000.0, 1200000.0,
                    EmploymentType.FULL_TIME, ExperienceLevel.MID, saved);
            job.addTag(remoteTag);
            job.addTag(fullTimeTag);
            Job savedJob = jobRepo.save(job);
            System.out.println("Saved job ID: " + savedJob.getId());
            System.out.println("Job tags: " + savedJob.getTags().size());

            // Fetch and verify
//            System.out.println("Found: " + savedJob.getTitle()
//                    + " | Tags: " + savedJob.getTags().size());

            // Count
//            System.out.println("Total companies: " + companyRepo.count());
//            System.out.println("Total jobs: " + jobRepo.count());

            // Test derived queries
            List<Job> techCorpJobs = jobRepo.findByCompanyId(saved.getId());
            System.out.println("TechCorp jobs: " + techCorpJobs.size());

            List<Job> remoteJobs = jobRepo.findJobsByTagName("Remote");
            System.out.println("Remote jobs: " + remoteJobs.size());

            List<Job> draftJobs = jobRepo.findByStatus(JobStatus.DRAFT);
            System.out.println("Draft jobs: " + draftJobs.size());

// Test application duplicate prevention
            boolean exists = applicationRepo.existsByJobIdAndCandidateId(
                    savedJob.getId(), 999L);
            System.out.println("Duplicate check (should be false): " + exists);
        };
    }

}
