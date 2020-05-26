package com.example.datastructurelib;

import java.math.BigDecimal;
import java.util.Date;

import javax.swing.plaf.synth.Region;

public class Transakcija {
    /**
     * Spremenljivke
     */
    private Lokacija lokacija;
    private String datum;
    private Double znesek;
    private Boolean zapravljeno; //Če zapravi je 1, drugače 0

    /**
     * Constructor
     * @param lokacija
     * @param datum
     * @param znesek
     * @param zapravljeno
     */
    public Transakcija(Lokacija lokacija, String datum, Double znesek, Boolean zapravljeno) {
        this.lokacija = lokacija;
        this.datum = datum;
        this.znesek = znesek;
        this.zapravljeno = zapravljeno;
    }

    /**
     * Getters, setters
     * @return
     */
    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Double getZnesek() {
        return znesek;
    }

    public void setZnesek(Double znesek) {
        this.znesek = znesek;
    }

    public Boolean getZapravljeno() {
        return zapravljeno;
    }

    public void setZapravljeno(Boolean zapravljeno) {
        this.zapravljeno = zapravljeno;
    }

    @Override
    public String toString() {
        String stanje;
        stanje = zapravljeno?"Odhodek":"Prihodek";

        return  stanje +
                "\nPodatki transakcije " +
                "\nLokacija= " + lokacija +
                "\nDatum= " + datum +
                "\nZnesek= " + znesek;
    }
}
