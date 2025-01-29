package com.example.demo.model;

import com.example.demo.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String gender;
    private String dateOfBirth;

    // List to store medical records specific to this patient
    private List<FicheMedicale> fichesMedicales = new ArrayList<>();

    // Constructor
    public Patient(int id, String nom, String prenom, String adresse, String telephone, String gender, String dateOfBirth) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    // Default Constructor
    public Patient() {}

    public String getNom() {
        return nom;
    }

    public static int getId(String nom) {
        String sql = "SELECT id FROM patients WHERE nom = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nom);  // Set the patient name parameter
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); // Return the ID if found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if no patient is found
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<FicheMedicale> getFichesMedicales() {
        return fichesMedicales;
    }

    public void addFicheMedicale(FicheMedicale ficheMedicale) {
        this.fichesMedicales.add(ficheMedicale);
    }

    public void removeFicheMedicale(FicheMedicale ficheMedicale) {
        this.fichesMedicales.remove(ficheMedicale);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", fichesMedicales=" + fichesMedicales +
                '}';
    }
}
