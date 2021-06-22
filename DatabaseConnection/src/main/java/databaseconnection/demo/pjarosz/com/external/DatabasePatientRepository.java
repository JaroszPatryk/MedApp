package databaseconnection.demo.pjarosz.com.external;

import databaseconnection.demo.pjarosz.com.domain.Patient;


public class DatabasePatientRepository {

    public static Patient toDomain(PatientEntity patientEntity){
        return new Patient(
                patientEntity.getPatientId(),
                patientEntity.getPersonalId());
    }

    public static PatientEntity toEntity(Patient patient){
        return new PatientEntity(
                patient.getPersonalId());
    }



}
