package com.example.JobAssignmentAPI.job;

import com.example.JobAssignmentAPI.temp.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class JobController {
    @Autowired
    JobService jobService;

    @Autowired
    TempService tempService;

    //@PostMapping("/jobs")
    @RequestMapping(value = "/jobs", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Job createJob(@RequestBody JobCreateDTO jobWithTempDTO, Job job) {
        if (jobWithTempDTO.getTempId() != null) {
            return jobService.createJob1(jobWithTempDTO);
        }
        return jobService.createJob(jobWithTempDTO);
    }

    @GetMapping("/jobs")
    public List<Job> getAllJobs(@RequestParam(required = false) Boolean assigned) {
        if (assigned == null) {
            return jobService.getAllJobs();
        }
        return (jobService.getAssignedJobs(assigned));

    }

    @RequestMapping(value = "/jobs/{jobId}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Job updateJob(@PathVariable(value = "jobId") Long jobId, @RequestBody JobCreateDTO jobCreationRequest) {
        return jobService.createJob2(jobId, jobCreationRequest);
    }

    @GetMapping("/jobs/{jobId}")
    public Job getJobById(@PathVariable(value = "jobId") Long jobId) {
        return jobService.getJobById(jobId);
    }

}
