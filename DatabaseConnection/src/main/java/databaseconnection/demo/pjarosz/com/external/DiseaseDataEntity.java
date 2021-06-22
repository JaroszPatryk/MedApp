package databaseconnection.demo.pjarosz.com.external;

import databaseconnection.demo.pjarosz.com.domain.Patient;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "disease_Data")
public class DiseaseDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diseaseId;
    @Column
    private String descriptionDisease;
    @Column
    private String medication;
    @Column
    private String personalId;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private PatientEntity patientEntity;

    public DiseaseDataEntity(String descriptionDisease, String medication, PatientEntity patientEntity) {
        this.descriptionDisease = descriptionDisease;
        this.medication = medication;
        this.patientEntity = patientEntity;
    }

}
