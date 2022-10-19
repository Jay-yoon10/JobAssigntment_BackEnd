package com.example.JobAssignmentAPI.temp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TempRepository extends JpaRepository<Temp, Long> {

    @Query(value = "SELECT * FROM temp, job WHERE temp.id = job.temp_id AND job.end_date <= :startDate", nativeQuery = true)
    List<Temp> newAvailableTemps(LocalDate startDate);

}
