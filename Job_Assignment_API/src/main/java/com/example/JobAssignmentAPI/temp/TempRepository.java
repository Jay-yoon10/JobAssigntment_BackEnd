package com.example.JobAssignmentAPI.temp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TempRepository extends JpaRepository<Temp, Long> {


    @Query(value = "SELECT * FROM temp WHERE NOT temp.id in (SELECT job.temp_id FROM job WHERE job.temp_id IS NOT NULL)", nativeQuery = true)
    List<Temp> tempsWithoutJobs();

    @Query(value = "SELECT * FROM temp LEFT JOIN job ON temp.id = job.temp_id WHERE job.end_date <= :startDate", nativeQuery = true)
    List<Temp> tempsWithoutJobs(LocalDate startDate);

}
