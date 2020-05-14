package com.example.datastructurelib;

import java.util.ArrayList;
import java.util.List;

public class SeznamVrstRacuna {
    public ArrayList<VrstaRacuna> seznamRacunov;

    public SeznamVrstRacuna(ArrayList<VrstaRacuna> seznamRacunov) {
        this.seznamRacunov = seznamRacunov;
    }

    public SeznamVrstRacuna() {
        this.seznamRacunov = new ArrayList<VrstaRacuna>();
    }

    public void setSeznamRacunov(ArrayList<VrstaRacuna> seznamRacunov) {
        this.seznamRacunov = seznamRacunov;
    }

    public ArrayList<VrstaRacuna> getSeznamRacunov() {
        return seznamRacunov;
    }

    public void dodajRacun(VrstaRacuna novaVrsta){
        this.seznamRacunov.add(novaVrsta);
    }
}
