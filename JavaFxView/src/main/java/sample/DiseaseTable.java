package sample;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class DiseaseTable {

    private final SimpleLongProperty diseaseId;
    private final SimpleStringProperty descriptionDisease;
    private final SimpleStringProperty medication;


    public DiseaseTable(Long diseaseId, String descriptionDisease, String medication) {
        this.diseaseId = new SimpleLongProperty(diseaseId);
        this.descriptionDisease = new SimpleStringProperty(descriptionDisease);
        this.medication = new SimpleStringProperty(medication);
    }

    public long getDiseaseId() {
        return diseaseId.get();
    }
    public String getDescriptionDisease(){
        return descriptionDisease.get();
    }
    public String getMedication(){
        return medication.get();
    }

    public static DiseaseTable of(DiseaseData diseaseData){
        return new DiseaseTable(diseaseData.getDiseaseId(), diseaseData.getDescriptionDisease(),
                diseaseData.getMedication());
    }


}
