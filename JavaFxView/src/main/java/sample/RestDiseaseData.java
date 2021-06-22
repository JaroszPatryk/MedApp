package sample;

import sample.DiseaseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class RestDiseaseData {

    private static String DISEASE_URL = "http://localhost:8080/diseaseData";
    private static String CREATE_DISEASE_URL = "http://localhost:8080/diseaseData/add";
    private final RestTemplate restTemplate;

    public RestDiseaseData() {
        this.restTemplate = new RestTemplate();
    }

    public List<DiseaseData> getDisease(){
        ResponseEntity<DiseaseData[]> diseaseResponse = restTemplate.getForEntity(DISEASE_URL, DiseaseData[].class);
    return Arrays.asList(diseaseResponse.getBody());
    }

    public void saveDisease(DiseaseData diseaseData){
        ResponseEntity<DiseaseData> responseEntity = restTemplate.postForEntity(CREATE_DISEASE_URL, diseaseData, DiseaseData.class);

    }

    public void deleteDisease(Long diseaseId){{
    restTemplate.delete(DISEASE_URL + "/" + diseaseId);}
    }
}
