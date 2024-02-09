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
    @Column(name = "partit")
    private String partit;
    @Basic
    @Column(name = "preu")
    private int preu;
    @Basic
    @Column(name = "poblacio")
    private String poblacio;
    @Basic
    @Column(name = "dia")
    private Date dia;
    @Basic
    @Column(name = "hora_inici")
    private Time horaInici;
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

    public String getPartit() {
        return partit;
    }

    public void setPartit(String partit) {
        this.partit = partit;
    }

    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
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

    public Time getHoraInici() {
        return horaInici;
    }

    public void setHoraInici(Time horaInici) {
        this.horaInici = horaInici;
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
        if (preu != partits.preu) return false;
        if (partit != null ? !partit.equals(partits.partit) : partits.partit != null) return false;
        if (poblacio != null ? !poblacio.equals(partits.poblacio) : partits.poblacio != null) return false;
        if (dia != null ? !dia.equals(partits.dia) : partits.dia != null) return false;
        if (horaInici != null ? !horaInici.equals(partits.horaInici) : partits.horaInici != null) return false;
        if (horaAcaba != null ? !horaAcaba.equals(partits.horaAcaba) : partits.horaAcaba != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (partit != null ? partit.hashCode() : 0);
        result = 31 * result + preu;
        result = 31 * result + (poblacio != null ? poblacio.hashCode() : 0);
        result = 31 * result + (dia != null ? dia.hashCode() : 0);
        result = 31 * result + (horaInici != null ? horaInici.hashCode() : 0);
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
