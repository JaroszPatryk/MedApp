package databaseconnection.demo.pjarosz.com.external;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaPatientRepository extends JpaRepository<PatientEntity, Long> {

    @Query("select distinct pe from PatientEntity pe where upper(pe.personalId) like concat('%',upper(?1), '%')")
    PatientEntity findByPersonalId(String personalId);



}
