package com.example.JobAssignmentAPI.service;

import com.example.JobAssignmentAPI.model.Job;
import com.example.JobAssignmentAPI.model.Temp;
import com.example.JobAssignmentAPI.repository.TempRepository;
import com.example.JobAssignmentAPI.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TempService {

    @Autowired
    TempRepository tempRepository;

    private final JobService jobService;

    public TempService(JobService jobService) {
        this.jobService = jobService;
    }


    public Temp createTemp(Temp temp) {
        return tempRepository.save(temp);
    }

    public List<Temp> getAllTemps() {

        return tempRepository.findAll();


    }

    public Optional<Temp> getTempById(Long tempId) {

        return tempRepository.findById(tempId);
    }

    public List<Temp> getAvailableTemps(Long jobId) {

        List<Temp> newAvailableTemps = new ArrayList<>();

        Optional<Job> jobById = jobService.getJobById(jobId);
        Job newJob = new Job();
        if (jobById.isEmpty()) {
            throw new RuntimeException("Cannot find a job with given Id");
        }
        newJob.setStartDate(jobById.get().getStartDate());
        List<Temp> availableTemps = tempRepository.tempsWithoutJobs(newJob.getStartDate());
        List<Temp> tempsWithoutJobs = tempRepository.tempsWithoutJobs();
        availableTemps.removeIf(temp -> temp.getJobs().stream().anyMatch(job -> job.getEndDate().isAfter(newJob.getStartDate())));
        newAvailableTemps.addAll(tempsWithoutJobs);
        newAvailableTemps.addAll(availableTemps);
        newAvailableTemps.sort(Comparator.comparing(Temp::getId));

        return newAvailableTemps;

    }

}
