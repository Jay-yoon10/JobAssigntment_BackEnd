package com.example.JobAssignmentAPI.job;

import com.example.JobAssignmentAPI.temp.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")

public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    TempService tempService;

    //GET /jobs - Creates a job
    @RequestMapping(value = "/jobs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Job createJob(@RequestBody JobCreateDTO jobWithTempDTO) {
        if (jobWithTempDTO.getTempId() != null) {
            return jobService.createJob1(jobWithTempDTO);
        }
        return jobService.createJob(jobWithTempDTO);
    }

    //GET /jobs - Get the list of jobs
    //GET /jobs?assigned={false|true} - optional "assigned" param to get assigned/unassigned jobs
    @GetMapping("/jobs")
    public List<Job> getAllJobs(@RequestParam(required = false) Boolean assigned) {
        if (assigned == null) {
            return jobService.getAllJobs();
        }
        return (jobService.getAssignedJobs(assigned));

    }
    //PATCH /jobs/{jobId} - Can update the fields for jobs depending on the requestBody
    @RequestMapping(value = "/jobs/{jobId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Job updateJob(@PathVariable(value = "jobId") Long jobId, @RequestBody JobCreateDTO jobCreationRequest) {
        return jobService.createJob2(jobId, jobCreationRequest);
    }

    //GET /jobs/{jobId} - Get a job by given id
    @GetMapping("/jobs/{jobId}")
    public Job getJobById(@PathVariable(value = "jobId") Long jobId) {
        return jobService.getJobById(jobId);
    }

}
