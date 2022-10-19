package com.example.JobAssignmentAPI.job;

import com.example.JobAssignmentAPI.temp.Temp;
import com.example.JobAssignmentAPI.temp.TempWithJobDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "job")
public class Job implements Serializable {
    @Column(name = "id", nullable = false, length = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank(message = "Enter the job's name")
    private String name;

    @Column(name = "start_date")

    private LocalDate startDate;

    @Column(name = "end_date")

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Temp temp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @JsonIgnore
    public Temp getTemp() {
        return temp;
    }
//    public Job createJob2(Long jobId, JobRequestDTO job){
//
//        Set<Job> jobs = new HashSet<>();
//        Temp temp = new Temp();
//        Optional<Job> jobById = jobRepository.findById(jobId);
//        Optional<Temp> byId = tempRepository.findById(job.getTempId());
//        if(!byId.isPresent()){
//            throw new ResourceNotFoundException("Temp not found");
//        }
//        Temp tempById = byId.get();
//        Job jobToCreate = jobById.get();
//        // Tie Temp to Job
//        jobToCreate.setTemp(tempById);
//        BeanUtils.copyProperties(job, jobToCreate);
//        Job job2 = jobRepository.save(jobToCreate);
//
//        // Tie Job to Temp
//        jobs.add(job2);
//        temp.setJobs(jobs);
//
//        return job2;
//
//    }

    //    public void setTempId (Long tempId){
//        temp.setId(tempId);
//    }
//
//    public Long getTempId () {
//        return temp.getId();
//    }
    public TempWithJobDTO getAssignedTemp() {
        if (temp == null) {
            return null;
        }
        TempWithJobDTO temp2 = new TempWithJobDTO();
        temp2.setId(temp.getId());
        temp2.setFirstName(temp.getFirstName());
        temp2.setLastName(temp.getLastName());

        return temp2;

    }


    @JsonIgnore
    public void setTemp(Temp temp) {
        this.temp = temp;
    }
}

