package com.example.JobAssignmentAPI.job;

import com.example.JobAssignmentAPI.temp.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    TempService tempService;

    //POST /jobs - Creates a job
    @RequestMapping(value = "/jobs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createJob(@RequestBody JobCreateDTO jobWithTempDTO) {
        if (jobWithTempDTO != null) {
            if (jobWithTempDTO.getName().isEmpty()) {
                return ResponseEntity.badRequest().body("Please provide name of job");
            }
            if (jobWithTempDTO.getStartDate().isAfter(jobWithTempDTO.getEndDate())) {
                return ResponseEntity.badRequest().body("Start date cannot be later than End date");
            }
            if (jobWithTempDTO.getTempId() != null) {
                return ResponseEntity.ok(jobService.createJob1(jobWithTempDTO));
            }
            return ResponseEntity.ok(jobService.createJob(jobWithTempDTO));
        }
        return ResponseEntity.badRequest().body("There must be some insufficient data with your request");
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
    @RequestMapping(value = "/jobs/{jobId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateJob(@PathVariable(value = "jobId") Long jobId, @RequestBody JobCreateDTO jobCreationRequest) {
        if (jobService.createJob2(jobId, jobCreationRequest) == null) {
            return ResponseEntity.badRequest().body("Job cannot be found with given ID");

        }
        return ResponseEntity.ok(jobService.createJob2(jobId, jobCreationRequest));

    }

    //GET /jobs/{jobId} - Get a job by given id
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Object> getJobById(@PathVariable(value = "jobId") Long jobId) {
        Job jobFoundById = jobService.getJobById(jobId);
        if (jobFoundById != null) {
            return ResponseEntity.ok(jobService.getJobById(jobId));

        }
        return ResponseEntity.badRequest().body("A job cannot be found with given ID");
    }

}
