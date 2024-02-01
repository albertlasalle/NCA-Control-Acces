package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Usuaris {

    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "correu")
    private String correu;
    @Basic
    @Column(name = "edat")
    private int edat;
    @Basic
    @Column(name = "telefon")
    private int telefon;
    @Basic
    @Column(name = "contrasenya")
    private String contrasenya;
    @OneToMany(mappedBy = "usuarisByIdUsuari")
    private Collection<Tickets> ticketsById;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuaris usuaris = (Usuaris) o;

        if (id != usuaris.id) return false;
        if (edat != usuaris.edat) return false;
        if (telefon != usuaris.telefon) return false;
        if (nom != null ? !nom.equals(usuaris.nom) : usuaris.nom != null) return false;
        if (correu != null ? !correu.equals(usuaris.correu) : usuaris.correu != null) return false;
        if (contrasenya != null ? !contrasenya.equals(usuaris.contrasenya) : usuaris.contrasenya != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (correu != null ? correu.hashCode() : 0);
        result = 31 * result + edat;
        result = 31 * result + telefon;
        result = 31 * result + (contrasenya != null ? contrasenya.hashCode() : 0);
        return result;
    }

    public Collection<Tickets> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Tickets> ticketsById) {
        this.ticketsById = ticketsById;
    }
}
