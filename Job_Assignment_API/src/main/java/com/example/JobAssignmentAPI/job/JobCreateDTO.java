package com.example.JobAssignmentAPI.job;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class JobCreateDTO {

    @NotNull(message = "Please enter job name")
    private String name;
    @NotNull(message = "Please enter job name")
    private LocalDate startDate;
    @NotNull(message = "Please enter job name")
    private LocalDate endDate;

    private Long tempId;
}
