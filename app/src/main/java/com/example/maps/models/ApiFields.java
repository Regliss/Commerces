package com.example.maps.models;

public class ApiFields {

    private String nom_du_commerce;
    private String adresse;
    private int telephone;
    private String services;
    private String mail;
    private String fabrique_a_paris;
    private String type_de_commerce;

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFabrique_a_paris() {
        return fabrique_a_paris;
    }

    public void setFabrique_a_paris(String fabrique_a_paris) {
        this.fabrique_a_paris = fabrique_a_paris;
    }

    public String getType_de_commerce() {
        return type_de_commerce;
    }

    public void setType_de_commerce(String type_de_commerce) {
        this.type_de_commerce = type_de_commerce;
    }

    public String getSite_internet() {
        return site_internet;
    }

    public void setSite_internet(String site_internet) {
        this.site_internet = site_internet;
    }

    private String site_internet;

    public String getNom_du_commerce() {
        return nom_du_commerce;
    }

    public void setNom_du_commerce(String nom_du_commerce) {
        this.nom_du_commerce = nom_du_commerce;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }
}
