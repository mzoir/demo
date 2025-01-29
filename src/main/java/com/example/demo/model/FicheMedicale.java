package com.example.demo.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class FicheMedicale {
    private final IntegerProperty id;
    private final IntegerProperty patientId;
    private final StringProperty description;
    private final StringProperty dateCreation;

    public FicheMedicale(int id, int patientId, String description, String dateCreation, LocalDate date) {
        this.id = new SimpleIntegerProperty(id);
        this.patientId = new SimpleIntegerProperty(patientId);
        this.description = new SimpleStringProperty(description);
        this.dateCreation = new SimpleStringProperty(dateCreation);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public int getPatientId() { return patientId.get(); }
    public IntegerProperty patientIdProperty() { return patientId; }

    public String getDescription() { return description.get(); }
    public StringProperty descriptionProperty() { return description; }

    public String getDateCreation() { return dateCreation.get(); }
    public StringProperty dateCreationProperty() { return dateCreation; }
}
