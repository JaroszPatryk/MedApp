package sample;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientTable {

    private final SimpleLongProperty patientId;
    private final SimpleStringProperty personalId;


    public PatientTable(Long patientId, String personalId) {
        this.patientId = new SimpleLongProperty(patientId);
        this.personalId = new SimpleStringProperty(personalId);
    }

    public long getPatientId() {
        return patientId.get();
    }

    public String getPersonalId(){
        return personalId.get();
    }

    public static PatientTable of(Patient patient){
        return new PatientTable(patient.getPatientId(), patient.getPersonalId());
    }


}
