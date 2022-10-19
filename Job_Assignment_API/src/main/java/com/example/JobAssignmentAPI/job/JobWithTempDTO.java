package com.example.JobAssignmentAPI.job;

import com.example.JobAssignmentAPI.temp.Temp;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobWithTempDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Temp temp;
}
