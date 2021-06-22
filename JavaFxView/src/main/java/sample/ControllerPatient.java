/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerPatient implements Initializable {

    private final RestPatient restPatient;
    private final RestDiseaseData restDiseaseData;
    private final ObservableList<PatientTable> listPatient = FXCollections.observableArrayList();
    private final ObservableList<DiseaseTable> listDisease = FXCollections.observableArrayList();
    Logger logger = LoggerFactory.getLogger(ControllerPatient.class);

    public ControllerPatient() {
        this.restPatient = new RestPatient();
        this.restDiseaseData = new RestDiseaseData();
    }

    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private TextField filterField;
    @FXML
    private TextField descriptionDiseaseField;
    @FXML
    private TextField medicationField;
    @FXML
    private TextField personalIdField;

    @FXML
    private TableColumn<Patient, Integer> idPatient;
    @FXML
    private TableColumn<DiseaseData, Integer> idDisease;
    @FXML
    private TableColumn<Patient, Integer> personalId;
    @FXML
    private TableColumn<DiseaseData, String> descriptionDisease;
    @FXML
    private TableColumn<DiseaseData, String> personal_Id;
    @FXML
    private TableColumn<DiseaseData, String> medication;
    @FXML
    private TableView<PatientTable> tableviewPatient;
    @FXML
    private TableView<DiseaseTable> tableviewDisease;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializePatientView();
        initializeDiseaseView();
        addButtonAction();
        filtredList();
        deleteButtonAction();
    }

    private void addButtonAction() {
        add.setOnAction((x) -> {    boolean diseaseDataIsEmpty = personalIdField.getText().isEmpty() || descriptionDiseaseField.getText().isEmpty() ||
                medicationField.getText().isEmpty();
            boolean personalIdFieldIsNotEmpty = !personalIdField.getText().isEmpty() && (descriptionDiseaseField.getText().isEmpty() ||
                    medicationField.getText().isEmpty());
            if (!diseaseDataIsEmpty) {
                DiseaseData diseaseData = createDiseaseData();
                restDiseaseData.saveDisease(diseaseData);
            } else if (personalIdFieldIsNotEmpty) {
                Patient patient = createPatient();
                restPatient.savePatient(patient);
            }
        });
    }

    public void deleteButtonAction() {
        delete.setOnAction((x) -> {
        PatientTable patientSelected;
        DiseaseTable diseaseDataSelected;
        patientSelected = tableviewPatient.getSelectionModel().getSelectedItem();
        diseaseDataSelected = tableviewDisease.getSelectionModel().getSelectedItem();
        if (diseaseDataSelected != null) {
            patientSelected = null;
            restDiseaseData.deleteDisease(diseaseDataSelected.getDiseaseId());
            logger.debug("Choroba została usunieta z karty");

        } else if (patientSelected != null) {
            restPatient.deletePatient(patientSelected.getPatientId());
            logger.debug("Pacjent został usunięty");
        }
        loadDisease();
        loadPatient();
    });
    }

    private Patient createPatient() {
        String personalId = personalIdField.getText();
        Patient patient = new Patient();
        patient.setPersonalId(personalId);
        return patient;
    }

    private DiseaseData createDiseaseData() {
        String personalId = personalIdField.getText();
        String descriptionDisease = descriptionDiseaseField.getText();
        String medication = medicationField.getText();
        DiseaseData diseaseData = new DiseaseData();
        diseaseData.setPersonalId(personalId);
        diseaseData.setDescriptionDisease(descriptionDisease);
        diseaseData.setMedication(medication);
        return diseaseData;
    }



    private void initializePatientView(){
        tableviewPatient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn idPatientColumn = new TableColumn("Lp.");
        idPatientColumn.setPrefWidth(7);
        idPatientColumn.setCellValueFactory(new PropertyValueFactory<PatientTable, Long>("patientId"));

        TableColumn personalIdColumn = new TableColumn("Pesel pacjenta");
        personalIdColumn.setMinWidth(15);
        personalIdColumn.setCellValueFactory(new PropertyValueFactory<PatientTable, String>("personalId"));

        tableviewPatient.getColumns().addAll(idPatientColumn, personalIdColumn);
        loadPatient();
        logger.debug("Wczytano pomyślnie dane do tabeli pacjentów");

    }

    private void initializeDiseaseView(){
        tableviewDisease.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn idDiseaseColumn = new TableColumn("Lp.");
        idDiseaseColumn.setPrefWidth(7);
        idDiseaseColumn.setCellValueFactory(new PropertyValueFactory<DiseaseTable, Long>("diseaseId"));


        TableColumn descriptionDiseaseColumn = new TableColumn("Opis choroby");
        descriptionDiseaseColumn.setPrefWidth(15);
        descriptionDiseaseColumn.setCellValueFactory(new PropertyValueFactory<DiseaseTable, String>("descriptionDisease"));

        TableColumn medicationColumn = new TableColumn("Zalecenia");
        medicationColumn.setPrefWidth(15);
        medicationColumn.setCellValueFactory(new PropertyValueFactory<DiseaseTable, String>("medication"));

        tableviewDisease.getColumns().addAll(idDiseaseColumn, descriptionDiseaseColumn, medicationColumn);
        loadDisease();
        logger.debug("Wczytano pomyślnie dane do tabeli chorób");
    }

    private void loadPatient() {
        Thread thread = new Thread(() -> {
            List<Patient> patient = restPatient.getPatients();
            List<PatientTable> patientTables = patient.stream().map(PatientTable::of).collect(Collectors.toList());
            this.listPatient.clear();
            this.listPatient.addAll(patientTables);
            tableviewPatient.setItems(listPatient);
        });
        thread.start();
    }

    private void loadDisease() {
        tableviewPatient.setOnMouseClicked(e -> {
            for (PatientTable patientTable : tableviewPatient.getSelectionModel().getSelectedItems()) {
                List<DiseaseData> diseaseData = restDiseaseData.getDisease();
                List<DiseaseTable> diseaseTables = diseaseData.stream().filter(c -> patientTable.getPersonalId()
                        .equals(c.getPersonalId())).map(DiseaseTable::of).collect(Collectors.toList());
                this.listDisease.clear();
                this.listDisease.addAll(diseaseTables);
                tableviewDisease.setItems(listDisease);

            }
        });


    }

    private void filtredList(){
        filterField.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                if(filterField.textProperty().get().isEmpty()) {
                    tableviewDisease.setItems(listDisease);
                    return;
                }
                ObservableList<DiseaseTable> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<DiseaseTable, ?>> cols = tableviewDisease.getColumns();
                for(int i=0; i<listDisease.size(); i++) {

                    for(int j=0; j<cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(listDisease.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if(cellValue.contains(filterField.textProperty().get().toLowerCase())) {
                            tableItems.add(listDisease.get(i));
                            break;
                        }
                    }
                }
                tableviewDisease.setItems(tableItems);

            }
        });
    }

}
