package com.example.JobAssignmentAPI.job;

import com.example.JobAssignmentAPI.exception.ResourceNotFoundException;
import com.example.JobAssignmentAPI.temp.Temp;
import com.example.JobAssignmentAPI.temp.TempRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class JobService {

    @Autowired
    TempRepository tempRepository;
    @Autowired
    JobRepository jobRepository;

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }


    public Job createJob(JobCreateDTO request) {
        Job job = new Job();
        job.setName(request.getName());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setTemp(null);

        return jobRepository.save(job);
    }

    public Job createJob1(JobCreateDTO request) {

        Set<Job> jobs = new HashSet<>();
        Temp temp = new Temp();

        Optional<Temp> tempById = tempRepository.findById(request.getTempId());
        List<Temp> newTemp = tempRepository.newAvailableTemps(request.getStartDate());
        if (newTemp.isEmpty()) {
            throw new ResourceNotFoundException("Temp is unavailable for the job");
        }
        Temp tempById2 = tempById.get();
        Job job = new Job();
        job.setName(request.getName());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setTemp(tempById2);
        BeanUtils.copyProperties(request, job);

        Job job2 = jobRepository.save(job);


        jobs.add(job2);
        temp.setJobs(jobs);

        return job2;
    }


    public Job createJob2(Long jobId, JobCreateDTO job) {

        Job jobById1 = jobRepository.findByNewId(jobId);

        boolean needUpdate = false;

        if (job.getName() != null) {
            jobById1.setName(job.getName());
            needUpdate = true;
        }
        if (job.getStartDate() != null) {
            jobById1.setStartDate(job.getStartDate());
            needUpdate = true;

        }
        if (job.getEndDate() != null) {
            jobById1.setEndDate(job.getEndDate());
            needUpdate = true;

        }

        if (job.getTempId() != null) {
            Optional<Job> jobById = jobRepository.findById(jobId);
            Optional<Temp> byId = tempRepository.findById(job.getTempId());
            Temp tempById = byId.get();

            jobById1.setTemp(tempById);
            needUpdate = true;

        }
        if (!needUpdate) {
            return jobById1;
        }

        return jobRepository.save(jobById1);

    }

    public List<Job> getAssignedJobs(Boolean assigned) {
        List<Job> job = assigned ? jobRepository.findByAssigned() : jobRepository.findByUnAssigned();
        return job;
    }


    public Job getJobById(Long jobId) {
        Job jobById = jobRepository.findByNewId(jobId);
        return jobById;
    }
}
