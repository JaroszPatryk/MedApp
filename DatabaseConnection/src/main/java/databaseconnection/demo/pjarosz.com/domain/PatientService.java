package databaseconnection.demo.pjarosz.com.domain;

import databaseconnection.demo.pjarosz.com.external.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {


    private final JpaPatientRepository jpaPatientRepository;
    private final JpaDiseaseDataRepository jpaDiseaseDataRepository;


    public PatientService(JpaPatientRepository jpaPatientRepository, JpaDiseaseDataRepository jpaDiseaseDataRepository) {
        this.jpaPatientRepository = jpaPatientRepository;

        this.jpaDiseaseDataRepository = jpaDiseaseDataRepository;
    }

    public PatientEntity findOne(String personalId){
        return jpaPatientRepository.findByPersonalId(personalId);
    }
    public Patient createPatient(Patient patient) {
        PatientEntity patientEntity = jpaPatientRepository.save(DatabasePatientRepository.toEntity(patient));
        return DatabasePatientRepository.toDomain(patientEntity);
    }

    public List<Patient> getAllPatient() {
        return jpaPatientRepository.findAll().stream().map(DatabasePatientRepository::toDomain).collect(Collectors.toList());

    }

    public PatientEntity savePatient(String personalId){
        PatientEntity patientEntity = new PatientEntity(personalId);
        return jpaPatientRepository.save(patientEntity);
    }

    public void deletePatient(Long patientId) {
        jpaPatientRepository.deleteById(patientId);
    }

    public DiseaseData createDiseaseData(DiseaseData diseaseData){
        PatientEntity patientEntity;
        if(findOne(diseaseData.getPersonalId()) == null){
            patientEntity = savePatient(diseaseData.getPersonalId());
        } else{
            patientEntity = findOne(diseaseData.getPersonalId());
        }
        return DatabaseDiseaseDataEntityRepository.toDomain(jpaDiseaseDataRepository.save(DatabaseDiseaseDataEntityRepository.toEntity(diseaseData, patientEntity)));
    }

public List<DiseaseData> getAllDiseaseData(){
        return jpaDiseaseDataRepository.findAll().stream().map(DatabaseDiseaseDataEntityRepository::toDomain).collect(Collectors.toList());
}



public List<DiseaseData> getAllDiseaseDataByPatientId(Long id){
        return jpaDiseaseDataRepository.findAllByPatientId(id).stream().map(DatabaseDiseaseDataEntityRepository::toDomain).collect(Collectors.toList());
}

public void deleteDisease(Long diseaseId){
        jpaDiseaseDataRepository.deleteById(diseaseId);
}

}
