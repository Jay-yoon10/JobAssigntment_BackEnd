package com.example.JobAssignmentAPI.temp;

import com.example.JobAssignmentAPI.job.Job;
import com.example.JobAssignmentAPI.job.JobRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        List<Temp> allTemps = tempRepository.tempsWithoutJobs();

        Job jobs = jobRepository.findByNewId(jobId);
        Job newJob = new Job();
        newJob.setStartDate(jobs.getStartDate());
        List<Temp> newTemp = tempRepository.newAvailableTemps(newJob.getStartDate());
//        BeanUtils.copyProperties(newTemp, allTemps);
        List<Temp> newList = Stream.concat(newTemp.stream(), allTemps.stream()) .collect(Collectors.toList());
        return newList;
    }

}
