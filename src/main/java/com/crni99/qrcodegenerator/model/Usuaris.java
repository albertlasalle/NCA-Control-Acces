package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Usuaris {

    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "dni")
    private String dni;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "edat")
    private int edat;
    @Basic
    @Column(name = "correu")
    private String correu;
    @Basic
    @Column(name = "telefon")
    private int telefon;
    @OneToMany(mappedBy = "usuarisByIdUsuari")
    private Collection<Tickets> ticketsById;
    @OneToMany(mappedBy = "usuarisByIdUsuari_0")
    private Collection<Tickets> ticketsById_0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuaris usuaris = (Usuaris) o;

        if (id != usuaris.id) return false;
        if (edat != usuaris.edat) return false;
        if (telefon != usuaris.telefon) return false;
        if (dni != null ? !dni.equals(usuaris.dni) : usuaris.dni != null) return false;
        if (nom != null ? !nom.equals(usuaris.nom) : usuaris.nom != null) return false;
        if (correu != null ? !correu.equals(usuaris.correu) : usuaris.correu != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dni != null ? dni.hashCode() : 0);
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + edat;
        result = 31 * result + (correu != null ? correu.hashCode() : 0);
        result = 31 * result + telefon;
        return result;
    }

    public Collection<Tickets> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Tickets> ticketsById) {
        this.ticketsById = ticketsById;
    }

    public Collection<Tickets> getTicketsById_0() {
        return ticketsById_0;
    }

    public void setTicketsById_0(Collection<Tickets> ticketsById_0) {
        this.ticketsById_0 = ticketsById_0;
    }
}
