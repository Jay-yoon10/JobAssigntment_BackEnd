package com.example.JobAssignmentAPI.job;

import com.example.JobAssignmentAPI.temp.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class JobController {
    @Autowired
    JobService jobService;


    //POST /jobs - Creates a job
    @PostMapping("/jobs")
    public ResponseEntity<Object> createJob(@RequestBody JobCreateDTO jobWithTempDTO) {
        return ResponseEntity.ok(jobService.createJob(jobWithTempDTO));
    }

    //GET /jobs - Get the list of jobs
    //GET /jobs?assigned={false|true} - optional "assigned" param to get assigned/unassigned jobs
    @GetMapping("/jobs")
    public ResponseEntity<List<Job>> getAllJobs(@RequestParam(required = false) Boolean assigned) {
        if (assigned == null) {
            return ResponseEntity.ok(jobService.getAllJobs());
        }
        return ResponseEntity.ok(jobService.getAssignedJobs(assigned));

    }

    //PATCH /jobs/{jobId} - Can update the fields for jobs depending on the requestBody
    @PatchMapping("/jobs/{jobId}")
    public ResponseEntity<Object> updateJob(@PathVariable(value = "jobId") Long jobId, @RequestBody JobCreateDTO jobCreationRequest) {
        return ResponseEntity.ok(jobService.updateJob(jobId, jobCreationRequest));

    }

    //GET /jobs/{jobId} - Get a job by given id
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Object> getJobById(@PathVariable(value = "jobId") Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }


    @DeleteMapping("/jobs/{jobId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long jobId) {
        jobService.deleteJobById(jobId);
        return ResponseEntity.ok().build();
    }

}
