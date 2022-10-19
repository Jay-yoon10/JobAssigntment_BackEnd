package com.example.JobAssignmentAPI.job;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query(value = "SELECT * FROM job WHERE job.temp_id IS NOT null", nativeQuery = true)
    List<Job> findByAssigned();

    @Query(value = "SELECT * FROM job job WHERE job.temp_id IS null", nativeQuery = true)
    List<Job> findByUnAssigned();


    @Query(value = "SELECT * FROM job WHERE job.id = :jobId", nativeQuery = true)
    Job findByNewId(Long jobId);


}
