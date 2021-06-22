package databaseconnection.demo.pjarosz.com.external;

import databaseconnection.demo.pjarosz.com.domain.DiseaseData;


public class DatabaseDiseaseDataEntityRepository {

    public static DiseaseData toDomain(DiseaseDataEntity diseaseDataEntity){
        return new DiseaseData(
                diseaseDataEntity.getDiseaseId(),
                diseaseDataEntity.getDescriptionDisease(),
                diseaseDataEntity.getMedication(),
                diseaseDataEntity.getPatientEntity().getPersonalId()
                );
    }

    public static DiseaseDataEntity toEntity(DiseaseData diseaseData, PatientEntity patientEntity){
        return new DiseaseDataEntity(
                diseaseData.getDescriptionDisease(),
                diseaseData.getMedication(),
                patientEntity);
    }



}
