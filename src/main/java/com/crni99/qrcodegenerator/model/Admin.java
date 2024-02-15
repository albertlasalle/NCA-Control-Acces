package com.crni99.qrcodegenerator.model;

import javax.persistence.*;

@Entity
public class Admin {

    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "usuari")
    private String usuari;
    @Basic
    @Column(name = "contrasenya")
    private String contrasenya;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuari() {
        return usuari;
    }

    public void setUsuari(String usuari) {
        this.usuari = usuari;
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

        Admin admin = (Admin) o;

        if (id != admin.id) return false;
        if (usuari != null ? !usuari.equals(admin.usuari) : admin.usuari != null) return false;
        if (contrasenya != null ? !contrasenya.equals(admin.contrasenya) : admin.contrasenya != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (usuari != null ? usuari.hashCode() : 0);
        result = 31 * result + (contrasenya != null ? contrasenya.hashCode() : 0);
        return result;
    }
}
