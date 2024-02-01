package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.Collection;

@Entity
public class Partits {

    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nom")
    private String nom;
    @Basic
    @Column(name = "poblacio")
    private String poblacio;
    @Basic
    @Column(name = "dia")
    private Date dia;
    @Basic
    @Column(name = "hora_começa")
    private Time horaComeça;
    @Basic
    @Column(name = "hora_acaba")
    private Time horaAcaba;
    @OneToMany(mappedBy = "partitsByIdPartit")
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

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Time getHoraComeça() {
        return horaComeça;
    }

    public void setHoraComeça(Time horaComeça) {
        this.horaComeça = horaComeça;
    }

    public Time getHoraAcaba() {
        return horaAcaba;
    }

    public void setHoraAcaba(Time horaAcaba) {
        this.horaAcaba = horaAcaba;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Partits partits = (Partits) o;

        if (id != partits.id) return false;
        if (nom != null ? !nom.equals(partits.nom) : partits.nom != null) return false;
        if (poblacio != null ? !poblacio.equals(partits.poblacio) : partits.poblacio != null) return false;
        if (dia != null ? !dia.equals(partits.dia) : partits.dia != null) return false;
        if (horaComeça != null ? !horaComeça.equals(partits.horaComeça) : partits.horaComeça != null) return false;
        if (horaAcaba != null ? !horaAcaba.equals(partits.horaAcaba) : partits.horaAcaba != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (poblacio != null ? poblacio.hashCode() : 0);
        result = 31 * result + (dia != null ? dia.hashCode() : 0);
        result = 31 * result + (horaComeça != null ? horaComeça.hashCode() : 0);
        result = 31 * result + (horaAcaba != null ? horaAcaba.hashCode() : 0);
        return result;
    }

    public Collection<Tickets> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Tickets> ticketsById) {
        this.ticketsById = ticketsById;
    }
}
