package com.example.JobAssignmentAPI.temp;

import com.example.JobAssignmentAPI.job.Job;
import com.example.JobAssignmentAPI.job.JobRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TempService {

    @Autowired
    TempRepository tempRepository;
    @Autowired
    JobRepository jobRepository;


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

        List<Temp> allTemps = tempRepository.findAll();
        Temp temp = new Temp();

        Job jobs = jobRepository.findByNewId(jobId);
        Job newJob = new Job();
        newJob.setStartDate(jobs.getStartDate());
        List<Temp> newTemp = tempRepository.newAvailableTemps(newJob.getStartDate());

        BeanUtils.copyProperties(temp, allTemps);
        BeanUtils.copyProperties(newTemp, temp);

        return newTemp;
    }

}
