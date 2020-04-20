package com.example.datastructurelib;

import java.util.Date;

import javax.swing.plaf.synth.Region;

public class Transakcija {
    /**
     * Spremenljivke
     */
    private Lokacija lokacija;
    private Date datum;
    private Float znesek;
    private Boolean zapravljeno; //Če zapravi je 1, drugače 0

    /**
     * Constructor
     * @param lokacija
     * @param datum
     * @param znesek
     * @param zapravljeno
     */
    public Transakcija(Lokacija lokacija, Date datum, Float znesek, Boolean zapravljeno) {
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

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Float getZnesek() {
        return znesek;
    }

    public void setZnesek(Float znesek) {
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
                "\nZnesek= " + znesek +
                "\nzapravljeno=" + zapravljeno;
    }
}
