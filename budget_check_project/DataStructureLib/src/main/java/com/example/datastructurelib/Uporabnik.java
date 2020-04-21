package com.example.datastructurelib;

import java.util.List;

public class Uporabnik {
    /**
     * Spremenljivke
     */

    private String uporabnisko_ime;
    private String email;
    private String ime;
    private String priimek;
    private String geslo;
    private String kraj;
    private List<Racun> racuni;
    /**
     * Konstruktor
     * @param uporabnisko_ime
     * @param email
     * @param ime
     * @param priimek
     * @param geslo
     * @param kraj
     * @param racuni
     */
    public Uporabnik(String uporabnisko_ime, String email, String ime, String priimek, String geslo, String kraj, List<Racun> racuni) {
        this.uporabnisko_ime = uporabnisko_ime;
        this.email = email;
        this.ime = ime;
        this.priimek = priimek;
        this.geslo = geslo;
        this.kraj = kraj;
        this.racuni = racuni;
    }

    /**
     * Getters, setters
     * @return
     */
    public String getUporabnisko_ime() {
        return uporabnisko_ime;
    }

    public void setUporabnisko_ime(String uporabnisko_ime) {
        this.uporabnisko_ime = uporabnisko_ime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public List<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(List<Racun> racuni) {
        this.racuni = racuni;
    }

    @Override
    public String toString() {
        String info="";

        return "Podatki uporabnika\n" +
                "Ime in priimek: " + ime + ' ' + priimek
                +"\nŠtevilo računov: " + racuni.size();

    }
}
