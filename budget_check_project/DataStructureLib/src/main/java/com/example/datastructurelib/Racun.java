package com.example.datastructurelib;

import java.util.List;

public class Racun {
    /**
     * Spremenljivke
     */
    private VrstaRacuna tipRacuna;
    private String stRacuna;
    private float stanje;
    private List<Transakcija> promet;

    /**
     * Konstruktor
     * @param tipRacuna
     * @param stRacuna
     * @param stanje
     * @param promet
     */
    public Racun(VrstaRacuna tipRacuna, String stRacuna, float stanje, List<Transakcija> promet) {
        this.tipRacuna = tipRacuna;
        this.stRacuna = stRacuna;
        this.stanje = stanje;
        this.promet = promet;
    }

    /**
     * Getters, setters
     * @return
     */
    public VrstaRacuna getTipRacuna() {
        return tipRacuna;
    }

    public void setTipRacuna(VrstaRacuna tipRacuna) {
        this.tipRacuna = tipRacuna;
    }

    public String getStRacuna() {
        return stRacuna;
    }

    public void setStRacuna(String stRacuna) {
        this.stRacuna = stRacuna;
    }

    public float getStanje() {
        return stanje;
    }

    public void setStanje(float stanje) {
        this.stanje = stanje;
    }

    public List<Transakcija> getPromet() {
        return promet;
    }

    public void setPromet(List<Transakcija> promet) {
        this.promet = promet;
    }

    /**
     * Izpis prometa
     * @return
     */
    public String transactionList(){
        String output="";

        for(int i = 0; i<promet.size(); i++){
            output+=promet.get(i).toString();
        }

        return output;
    }

    /**
     * Izpis podatkov računa
     * @return
     */
    @Override
    public String toString() {
        return  tipRacuna +
                "\nŠtevilka računa='" + stRacuna + '\'' +
                "\nStanje=" + stanje +
                "\nPromet=" ;
    }
}
