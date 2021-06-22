package databaseconnection.demo.pjarosz.com.external;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;
    @Column
    private String personalId;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personalId")
    private List<DiseaseDataEntity> diseaseData;

    public PatientEntity( String personalId) {

        this.personalId = personalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientEntity that = (PatientEntity) o;
        return personalId == that.personalId &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(diseaseData, that.diseaseData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, personalId, diseaseData);
    }
}
