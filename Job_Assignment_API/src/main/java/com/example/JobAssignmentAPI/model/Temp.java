package com.example.JobAssignmentAPI.model;

import com.example.JobAssignmentAPI.model.Job;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "temp")
@Getter
@Setter
public class Temp implements Serializable {

    @Column(name = "id", nullable = false, length = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "Enter the temp's first name")
    @NotBlank(message = "Enter the temp's first name")
    public String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Enter the temp's first name")
    @NotBlank(message = "Enter the temp's last name")
    public String lastName;

    @OneToMany(mappedBy = "temp", fetch = FetchType.LAZY)
    private Set<Job> jobs = new HashSet<>();


}
