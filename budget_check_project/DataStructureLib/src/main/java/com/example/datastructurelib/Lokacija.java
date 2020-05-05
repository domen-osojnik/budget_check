package com.example.datastructurelib;

public class Lokacija {
    /**
     * Spremenljivke
     */

    private String naziv;

    /**
     * Konstruktor
     * @param naziv
     * @param postnaStevilka
     */
    public Lokacija(String naziv, String postnaStevilka) {
        this.naziv = naziv;
    }

    /**
     * Getters, setters
     * @return
     */
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }


}
