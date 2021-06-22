package databaseconnection.demo.pjarosz.com.external;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaDiseaseDataRepository extends JpaRepository<DiseaseDataEntity, Long> {
    @Query("select distinct pe from DiseaseDataEntity pe where upper(pe.patientEntity.patientId) like concat('%',upper(?1), '%')")
    List<DiseaseDataEntity> findAllByPatientId(Long patientId);

}
