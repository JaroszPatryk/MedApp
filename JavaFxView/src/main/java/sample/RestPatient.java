package sample;

import sample.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


public class RestPatient {

    private static String PATIENT_URL = "http://localhost:8080/patients";
    private static String CREATE_PATIENT_URL = "http://localhost:8080/patients/add";
    private final RestTemplate restTemplate;

    public RestPatient() {
        this.restTemplate = new RestTemplate();
    }

    public List<Patient> getPatients(){
        ResponseEntity<Patient[]> patientsResponse = restTemplate.getForEntity(PATIENT_URL, Patient[].class);
    return Arrays.asList(patientsResponse.getBody());
    }

    public void savePatient(Patient patient){
        ResponseEntity<Patient> responseEntity = restTemplate.postForEntity(CREATE_PATIENT_URL, patient, Patient.class);

    }

    public void deletePatient(Long patientId){{
    restTemplate.delete(PATIENT_URL + "/" + patientId);}
    }
}
