package com.example.JobAssignmentAPI.service;

import com.example.JobAssignmentAPI.dto.JobCreateDTO;
import com.example.JobAssignmentAPI.model.Job;
import com.example.JobAssignmentAPI.model.Temp;
import com.example.JobAssignmentAPI.repository.JobRepository;
import com.example.JobAssignmentAPI.repository.TempRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {JobService.class})
@ExtendWith(SpringExtension.class)
class JobServiceTest {
    @MockBean
    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;

    @MockBean
    private TempRepository tempRepository;


    @Test
    void testGetAllJobs() {
        ArrayList<Job> jobList = new ArrayList<>();
        when(jobRepository.findAll()).thenReturn(jobList);
        List<Job> actualAllJobs = jobService.getAllJobs();
        assertSame(jobList, actualAllJobs);
        assertTrue(actualAllJobs.isEmpty());
        verify(jobRepository).findAll();
    }


    @Test
    void testCreateJob() {
        Temp temp = new Temp();
        temp.setFirstName("Jane");
        temp.setId(123L);
        temp.setJobs(new HashSet<>());
        temp.setLastName("Doe");

        Job job = new Job();
        job.setEndDate(LocalDate.ofEpochDay(1L));
        job.setId(123L);
        job.setName("Name");
        job.setStartDate(LocalDate.ofEpochDay(1L));
        job.setTemp(temp);
        when(jobRepository.save(any())).thenReturn(job);
        when(jobRepository.findJobsWithoutTemp(any())).thenReturn(new ArrayList<>());

        Temp temp1 = new Temp();
        temp1.setFirstName("Jane");
        temp1.setId(123L);
        temp1.setJobs(new HashSet<>());
        temp1.setLastName("Doe");
        Optional<Temp> ofResult = Optional.of(temp1);
        when(tempRepository.findById(any())).thenReturn(ofResult);

        JobCreateDTO jobCreateDTO = new JobCreateDTO();
        jobCreateDTO.setEndDate(LocalDate.ofEpochDay(1L));
        jobCreateDTO.setName("Name");
        jobCreateDTO.setStartDate(LocalDate.ofEpochDay(1L));
        jobCreateDTO.setTempId(123L);
        assertEquals(1, jobService.createJob(jobCreateDTO).size());
        verify(jobRepository, atLeast(1)).save(any());
        verify(jobRepository).findJobsWithoutTemp(any());
        verify(tempRepository).findById(any());
        assertEquals("1970-01-02", jobCreateDTO.getEndDate().toString());
        assertEquals("1970-01-02", jobCreateDTO.getStartDate().toString());
        assertEquals("Name", jobCreateDTO.getName());
    }


    @Test
    void testUpdateJob() {
        Temp temp = new Temp();
        temp.setFirstName("Jane");
        temp.setId(123L);
        temp.setJobs(new HashSet<>());
        temp.setLastName("Doe");

        Job job = new Job();
        job.setEndDate(LocalDate.ofEpochDay(1L));
        job.setId(123L);
        job.setName("Name");
        job.setStartDate(LocalDate.ofEpochDay(1L));
        job.setTemp(temp);

        Temp temp1 = new Temp();
        temp1.setFirstName("Jane");
        temp1.setId(123L);
        temp1.setJobs(new HashSet<>());
        temp1.setLastName("Doe");

        Job job1 = new Job();
        job1.setEndDate(LocalDate.ofEpochDay(1L));
        job1.setId(123L);
        job1.setName("Name");
        job1.setStartDate(LocalDate.ofEpochDay(1L));
        job1.setTemp(temp1);
        when(jobRepository.save(any())).thenReturn(job1);
        when(jobRepository.findJobsWithoutTemp(any())).thenReturn(new ArrayList<>());
        when(jobRepository.findByNewId(any())).thenReturn(job);

        Temp temp2 = new Temp();
        temp2.setFirstName("Jane");
        temp2.setId(123L);
        temp2.setJobs(new HashSet<>());
        temp2.setLastName("Doe");
        Optional<Temp> ofResult = Optional.of(temp2);
        when(tempRepository.findById(any())).thenReturn(ofResult);

        JobCreateDTO jobCreateDTO = new JobCreateDTO();
        jobCreateDTO.setEndDate(LocalDate.ofEpochDay(1L));
        jobCreateDTO.setName("Name");
        jobCreateDTO.setStartDate(LocalDate.ofEpochDay(1L));
        jobCreateDTO.setTempId(123L);
        assertSame(job1, jobService.updateJob(123L, jobCreateDTO));
        verify(jobRepository).findByNewId(any());
        verify(jobRepository).save(any());
        verify(jobRepository).findJobsWithoutTemp(any());
        verify(tempRepository).findById(any());
    }



    @Test
    void testGetAssignedJobs() {
        ArrayList<Job> jobList = new ArrayList<>();
        when(jobRepository.findByAssigned()).thenReturn(jobList);
        List<Job> actualAssignedJobs = jobService.getAssignedJobs(true);
        assertSame(jobList, actualAssignedJobs);
        assertTrue(actualAssignedJobs.isEmpty());
        verify(jobRepository).findByAssigned();
    }



    @Test
    void testGetJobById() {
        when(jobRepository.findById(any())).thenThrow(new RuntimeException("An error occurred"));
        assertThrows(RuntimeException.class, () -> jobService.getJobById(123L));
        verify(jobRepository).findById(any());
    }


    @Test
    void testDeleteJobById() {
        doNothing().when(jobRepository).deleteById(any());
        jobService.deleteJobById(123L);
        verify(jobRepository).deleteById(any());
        assertTrue(jobService.getAllJobs().isEmpty());
    }

}

