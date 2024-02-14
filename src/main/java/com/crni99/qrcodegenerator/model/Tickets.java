package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Tickets {

    @Id
    @Column(name = "token")
    private String token;
    @Basic
    @Column(name = "id_partit")
    private int idPartit;
    @Basic
    @Column(name = "dni_usuari")
    private String dniUsuari;
    @Basic
    @Column(name = "nom_usuari")
    private String nomUsuari;
    @Basic
    @Column(name = "edat_usuari")
    private int edatUsuari;
    @Basic
    @Column(name = "correu")
    private String correu;
    @Basic
    @Column(name = "telefon_movil")
    private int telefonMovil;
    @Basic
    @Column(name = "data_compra")
    private Date dataCompra;
    @ManyToOne
    @JoinColumn(name = "id_partit", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Partits partitsByIdPartit;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIdPartit() {
        return idPartit;
    }

    public void setIdPartit(int idPartit) {
        this.idPartit = idPartit;
    }

    public String getDniUsuari() {
        return dniUsuari;
    }

    public void setDniUsuari(String dniUsuari) {
        this.dniUsuari = dniUsuari;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public int getEdatUsuari() {
        return edatUsuari;
    }

    public void setEdatUsuari(int edatUsuari) {
        this.edatUsuari = edatUsuari;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public int getTelefonMovil() {
        return telefonMovil;
    }

    public void setTelefonMovil(int telefonMovil) {
        this.telefonMovil = telefonMovil;
    }

    public Date getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tickets tickets = (Tickets) o;

        if (idPartit != tickets.idPartit) return false;
        if (edatUsuari != tickets.edatUsuari) return false;
        if (telefonMovil != tickets.telefonMovil) return false;
        if (token != null ? !token.equals(tickets.token) : tickets.token != null) return false;
        if (dniUsuari != null ? !dniUsuari.equals(tickets.dniUsuari) : tickets.dniUsuari != null) return false;
        if (nomUsuari != null ? !nomUsuari.equals(tickets.nomUsuari) : tickets.nomUsuari != null) return false;
        if (correu != null ? !correu.equals(tickets.correu) : tickets.correu != null) return false;
        if (dataCompra != null ? !dataCompra.equals(tickets.dataCompra) : tickets.dataCompra != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + idPartit;
        result = 31 * result + (dniUsuari != null ? dniUsuari.hashCode() : 0);
        result = 31 * result + (nomUsuari != null ? nomUsuari.hashCode() : 0);
        result = 31 * result + edatUsuari;
        result = 31 * result + (correu != null ? correu.hashCode() : 0);
        result = 31 * result + telefonMovil;
        result = 31 * result + (dataCompra != null ? dataCompra.hashCode() : 0);
        return result;
    }

    public Partits getPartitsByIdPartit() {
        return partitsByIdPartit;
    }

    public void setPartitsByIdPartit(Partits partitsByIdPartit) {
        this.partitsByIdPartit = partitsByIdPartit;
    }
}
