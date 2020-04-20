package com.example.datastructurelib;

public class Lokacija {
    /**
     * Spremenljivke
     */

    private String naziv;
    private String postnaStevilka;

    /**
     * Konstruktor
     * @param naziv
     * @param postnaStevilka
     */
    public Lokacija(String naziv, String postnaStevilka) {
        this.naziv = naziv;
        this.postnaStevilka = postnaStevilka;
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

    public String getPostnaStevilka() {
        return postnaStevilka;
    }

    public void setPostnaStevilka(String postnaStevilka) {
        this.postnaStevilka = postnaStevilka;
    }
}
