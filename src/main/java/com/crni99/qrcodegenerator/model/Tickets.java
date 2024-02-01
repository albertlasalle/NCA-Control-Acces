package com.crni99.qrcodegenerator.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Tickets {

    @Id
    @Column(name = "token")
    private String token;
    @Basic
    @Column(name = "id_usuari")
    private int idUsuari;
    @Basic
    @Column(name = "id_partit")
    private int idPartit;
    @Basic
    @Column(name = "data_compra")
    private Date dataCompra;
    @ManyToOne
    @JoinColumn(name = "id_usuari", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Usuaris usuarisByIdUsuari;
    @ManyToOne
    @JoinColumn(name = "id_partit", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Partits partitsByIdPartit;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    public int getIdPartit() {
        return idPartit;
    }

    public void setIdPartit(int idPartit) {
        this.idPartit = idPartit;
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

        if (idUsuari != tickets.idUsuari) return false;
        if (idPartit != tickets.idPartit) return false;
        if (token != null ? !token.equals(tickets.token) : tickets.token != null) return false;
        if (dataCompra != null ? !dataCompra.equals(tickets.dataCompra) : tickets.dataCompra != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + idUsuari;
        result = 31 * result + idPartit;
        result = 31 * result + (dataCompra != null ? dataCompra.hashCode() : 0);
        return result;
    }

    public Usuaris getUsuarisByIdUsuari() {
        return usuarisByIdUsuari;
    }

    public void setUsuarisByIdUsuari(Usuaris usuarisByIdUsuari) {
        this.usuarisByIdUsuari = usuarisByIdUsuari;
    }

    public Partits getPartitsByIdPartit() {
        return partitsByIdPartit;
    }

    public void setPartitsByIdPartit(Partits partitsByIdPartit) {
        this.partitsByIdPartit = partitsByIdPartit;
    }
}
