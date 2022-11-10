package com.example.JobAssignmentAPI.controller;

import com.example.JobAssignmentAPI.model.Temp;
import com.example.JobAssignmentAPI.service.JobService;
import com.example.JobAssignmentAPI.service.TempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TempController {
    @Autowired
    JobService jobService;

    @Autowired
    TempService tempService;

    // POST /temps - creates a temp
    @PostMapping("/temps")
    public ResponseEntity<Object> createTemp(@RequestBody @Valid Temp temp) {

        return ResponseEntity.ok(tempService.createTemp(temp));
    }

    // GET /temps - List all temps
    // GET /temps?jobId={jobId} - List temps that are available for a job based on the jobs date range
    @GetMapping("/temps")
    public ResponseEntity<Object> getAllTemps(@RequestParam(required = false) @PathVariable(value = "jobId") Long jobId) {
        if (jobId != null) {

            return ResponseEntity.ok(tempService.getAvailableTemps(jobId));
        }
        return ResponseEntity.ok(tempService.getAllTemps());
    }

    // GET /temps/{tempId} - Get a temp by its given Id
    @GetMapping("/temps/{tempId}")
    public ResponseEntity<Object> getTempById(@PathVariable(value = "tempId") Long tempId) {
        Optional<Temp> tempFoundBYId = tempService.getTempById(tempId);
        if (tempFoundBYId.isPresent()) {
            return ResponseEntity.ok(tempService.getTempById(tempId));

        }
        return ResponseEntity.badRequest().body("Cannot find a temp with given Id");
    }

}
