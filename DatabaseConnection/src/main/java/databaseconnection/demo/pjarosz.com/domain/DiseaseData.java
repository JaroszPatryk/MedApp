package databaseconnection.demo.pjarosz.com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiseaseData {

    private Long diseaseId;
    private String descriptionDisease;
    private String medication;
    private String personalId;
}
