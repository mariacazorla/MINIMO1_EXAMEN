package edu.upc.dsa.models;

public class Maleta {
    private String id;
    private String usuari;

    public Maleta(String id, String usuari) {
        this.id = id;
        this.usuari = usuari;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuari() { return usuari; }
    public void setUsuari(String usuari) { this.usuari = usuari; }

    @Override
    public String toString() {
        return "Maleta {id='" + id + "', usuari='" + usuari + "'}";
    }
}

