package com.example.JobAssignmentAPI.job;

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


    public Set<Job> createJob(JobCreateDTO request) {
        Job job = new Job();
        Set<Job> jobToCreate = new HashSet<>();
        Temp temp = new Temp();
        job.setName(request.getName());
        job.setStartDate(request.getStartDate());
        job.setEndDate(request.getEndDate());
        job.setTemp(null);

        if (request.getTempId() != null) {
            List<Job> jobList = jobRepository.findJobsWithoutTemp(request.getTempId());
            boolean isTempUnAssigned = jobList.stream().anyMatch(x -> x.getEndDate().isAfter(request.getStartDate()));
            Optional<Temp> tempById = tempRepository.findById(request.getTempId());

            if (isTempUnAssigned) {
                throw new RuntimeException("The temp has already a job assigned during given period.");
            }
            if (tempById.isEmpty()) {
                throw new RuntimeException("Cannot find the temp with given Id");

            }

            Temp validTempById = tempById.get();
            job.setName(request.getName());
            job.setStartDate(request.getStartDate());
            job.setEndDate(request.getEndDate());
            job.setTemp(validTempById);
            BeanUtils.copyProperties(request, job);

            Job jobToSave = jobRepository.save(job);
            BeanUtils.copyProperties(request, job);

            jobToCreate.add(jobToSave);
            temp.setJobs(jobToCreate);


            BeanUtils.copyProperties(job, request);
            Job job2 = jobRepository.save(job);
            jobToCreate.add(job2);
            return jobToCreate;

        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new RuntimeException("Start cannot be set after the job end date");
        }


        Job job1 = jobRepository.save(job);
        jobToCreate.add(job1);
        return jobToCreate;

    }


    public Job updateJob(Long jobId, JobCreateDTO job) {

        Job jobById = jobRepository.findByNewId(jobId);

        boolean needUpdate = false;
        if (jobById == null) {
            throw new RuntimeException("Invalid Job Id");
        }
        if (job.getName() != null) {
            jobById.setName(job.getName());
            needUpdate = true;
        }
        if (job.getStartDate() != null) {
            jobById.setStartDate(job.getStartDate());
            needUpdate = true;
        }
        if (job.getEndDate() != null) {
            jobById.setEndDate(job.getEndDate());
            needUpdate = true;
        }
        if (job.getTempId() != null) {

            List<Job> jobList = jobRepository.findJobsWithoutTemp(job.getTempId());
            System.out.println("???" + jobById.getStartDate());

            boolean isTempUnAssigned = jobList.stream().anyMatch(x -> x.getEndDate().isAfter(jobById.getStartDate()));
            if (isTempUnAssigned) {
                throw new RuntimeException("The temp already has a job assigned during the period.");
            }
            if (job.getTempId() == null) {
                throw new RuntimeException("Invalid or Unavailable Temp");
            }
            Optional<Temp> byId = tempRepository.findById(job.getTempId());
            Temp tempById = byId.get();

            jobById.setTemp(tempById);
            needUpdate = true;

        }
        if (!needUpdate) {
            return jobById;
        }

        return jobRepository.save(jobById);

    }

    public List<Job> getAssignedJobs(Boolean assigned) {
        return assigned ? jobRepository.findByAssigned() : jobRepository.findByUnAssigned();
    }


    public Optional<Job> getJobById(Long jobId) {
        return jobRepository.findById(jobId);
    }

    public void deleteJobById(Long id) {
        jobRepository.deleteById(id);
    }

}
