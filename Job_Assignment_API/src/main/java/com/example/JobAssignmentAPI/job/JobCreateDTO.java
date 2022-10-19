package com.example.JobAssignmentAPI.job;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobCreateDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long tempId;
}
