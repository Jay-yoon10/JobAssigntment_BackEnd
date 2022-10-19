package com.example.JobAssignmentAPI.temp;

import com.example.JobAssignmentAPI.job.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class TempController {
    @Autowired
    JobService jobService;

    @Autowired
    TempService tempService;

    //    --------------- Temps -------------------
    @PostMapping("/temps")
    public Temp createTemp(@RequestBody Temp temp, @RequestParam(required = false) Long jobId) {
        return tempService.createTemp(temp);
    }

    // GET / temps - List all temps
    // GET / temps?jobId={jobId} - List temps that are available for a job based on the jobs date range

    @GetMapping("/temps")
    public List<Temp> getAllTemps(@RequestParam(required = false) @PathVariable(value = "jobId") Long jobId) {
        if(jobId !=null){
            return tempService.getAvailableTemps(jobId);
        }
        return tempService.getAllTemps();
    }

    @GetMapping("/temps/{tempId}")
    public Optional<Temp> getTempById(@PathVariable(value = "tempId") Long tempId) {
        return tempService.getTempById(tempId);
    }

}
