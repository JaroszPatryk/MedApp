package databaseconnection.demo.controller;

import databaseconnection.demo.pjarosz.com.domain.Patient;
import databaseconnection.demo.pjarosz.com.domain.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @PostMapping("/add")
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        patientService.createPatient(patient);

        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public List<Patient> getAll() {
        return patientService.getAllPatient();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatient(@PathVariable("id") Long patientId) {
        patientService.deletePatient(patientId);
    }

}
