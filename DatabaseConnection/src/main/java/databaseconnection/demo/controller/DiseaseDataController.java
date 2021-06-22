package databaseconnection.demo.controller;

import databaseconnection.demo.pjarosz.com.domain.DiseaseData;
import databaseconnection.demo.pjarosz.com.domain.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diseaseData")
public class DiseaseDataController {
    private final PatientService patientService;

    public DiseaseDataController(PatientService patientService){
        this.patientService = patientService;
    }
    @PostMapping("/add")
    ResponseEntity<DiseaseData> create(@RequestBody DiseaseData diseaseData) {
        patientService.createDiseaseData(diseaseData);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    List<DiseaseData> getAll() {
        return patientService.getAllDiseaseData();
    }

    @GetMapping("/{id}")
    List<DiseaseData> getAllDiseaseDataByPatientId(@PathVariable Long id) {
        return patientService.getAllDiseaseDataByPatientId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteDisease(@PathVariable("id") Long diseaseId) {
        patientService.deleteDisease(diseaseId);
    }

}
