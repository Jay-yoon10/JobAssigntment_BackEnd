package com.example.JobAssignmentAPI.service;

import com.example.JobAssignmentAPI.model.Job;
import com.example.JobAssignmentAPI.model.Temp;
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

@ContextConfiguration(classes = {TempService.class})
@ExtendWith(SpringExtension.class)
class TempServiceTest {
    @MockBean
    private JobService jobService;

    @MockBean
    private TempRepository tempRepository;

    @Autowired
    private TempService tempService;


    @Test
    void testCreateTemp() {
        Temp temp = new Temp();
        temp.setFirstName("Jane");
        temp.setId(123L);
        temp.setJobs(new HashSet<>());
        temp.setLastName("Doe");
        when(tempRepository.save(any())).thenReturn(temp);

        Temp temp1 = new Temp();
        temp1.setFirstName("Jane");
        temp1.setId(123L);
        temp1.setJobs(new HashSet<>());
        temp1.setLastName("Doe");
        assertSame(temp, tempService.createTemp(temp1));
        verify(tempRepository).save(any());
    }


    @Test
    void testCreateTemp2() {
        when(tempRepository.save(any())).thenThrow(new RuntimeException("An error occurred"));

        Temp temp = new Temp();
        temp.setFirstName("Jane");
        temp.setId(123L);
        temp.setJobs(new HashSet<>());
        temp.setLastName("Doe");
        assertThrows(RuntimeException.class, () -> tempService.createTemp(temp));
        verify(tempRepository).save(any());
    }


    @Test
    void testGetAllTemps() {
        ArrayList<Temp> tempList = new ArrayList<>();
        when(tempRepository.findAll()).thenReturn(tempList);
        List<Temp> actualAllTemps = tempService.getAllTemps();
        assertSame(tempList, actualAllTemps);
        assertTrue(actualAllTemps.isEmpty());
        verify(tempRepository).findAll();
    }


    @Test
    void testGetAllTemps2() {
        when(tempRepository.findAll()).thenThrow(new RuntimeException("An error occurred"));
        assertThrows(RuntimeException.class, () -> tempService.getAllTemps());
        verify(tempRepository).findAll();
    }


    @Test
    void testGetTempById() {
        Temp temp = new Temp();
        temp.setFirstName("Jane");
        temp.setId(123L);
        temp.setJobs(new HashSet<>());
        temp.setLastName("Doe");
        Optional<Temp> ofResult = Optional.of(temp);
        when(tempRepository.findById(any())).thenReturn(ofResult);
        Optional<Temp> actualTempById = tempService.getTempById(123L);
        assertSame(ofResult, actualTempById);
        assertTrue(actualTempById.isPresent());
        verify(tempRepository).findById(any());
    }


    @Test
    void testGetAvailableTemps() {
        when(tempRepository.tempsWithoutJobs()).thenReturn(new ArrayList<>());
        when(tempRepository.tempsWithoutJobs(any())).thenReturn(new ArrayList<>());

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
        Optional<Job> ofResult = Optional.of(job);
        when(jobService.getJobById(any())).thenReturn(ofResult);
        assertTrue(tempService.getAvailableTemps(123L).isEmpty());
        verify(tempRepository).tempsWithoutJobs();
        verify(tempRepository).tempsWithoutJobs(any());
        verify(jobService).getJobById(any());
    }

}

