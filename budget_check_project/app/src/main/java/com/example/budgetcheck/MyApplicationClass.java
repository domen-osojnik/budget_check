package com.example.budgetcheck;

import android.app.Application;
import android.util.Log;

import com.example.budgetcheck.events.InfoEvent;
import com.example.datastructurelib.Racun;
import com.example.datastructurelib.SeznamVrstRacuna;
import com.example.datastructurelib.Transakcija;
import com.example.datastructurelib.Uporabnik;
import com.example.datastructurelib.VrstaRacuna;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.greenrobot.eventbus.EventBus;

public class MyApplicationClass extends Application {
    /**
     * Spremenljivke
     */
    public SeznamVrstRacuna seznamVrstRačunov;
    private DatabaseReference mDatabase;
    public boolean obstaja = false;
    public static final String TAG = MyApplicationClass.class.getName();
    public static final String MY_FILE_NAME = "DATA.json";
    static private Gson gson;
    static private File file;
    private String idAPP;
    public Uporabnik uporabnik;
    public String userId;

    /**
     * Ob zagonu
     */
    @Override
    public void onCreate() {
        super.onCreate();

        this.init();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public void setIdApp(String newId){
        this.idAPP = newId;
    }

    public String getIdApp(){
        return this.idAPP;
    }

    //region GSON
    /**
     * GSON
     * @return
     */
    private File getFile() {
        if (file == null) {
            File filesDir = getFilesDir();
            file = new File(filesDir,MY_FILE_NAME);
            try {
                file.createNewFile();
                EventBus.getDefault().post(new InfoEvent("File get", "File created."));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, file.getPath());
        return file;
    }

    public void saveToFile() {
        try {
            FileUtils.writeStringToFile(this.getFile(), getGson().toJson(this.seznamVrstRačunov));
            EventBus.getDefault().post(new InfoEvent("File write", "Written to file"));
        } catch (IOException e) {
            Log.d(TAG, "Can't save "+file.getPath());
        }
    }

    private boolean readFromFile() {
        if (!getFile().exists())  return false;
        try {
            seznamVrstRačunov = getGson().fromJson(FileUtils.readFileToString(getFile()), SeznamVrstRacuna.class);
        } catch (IOException e) {
            Log.d("READ", "Problem in read function!");
            return false;
        }
        return true;
    }

    public boolean init() {
        if (!readFromFile()) {
            return false;
        }
        if(seznamVrstRačunov == null){
            createAccountTypes();
            saveToFile();
        }
        return true;
    }

    public static Gson getGson() {
        if (gson==null)
            gson = new GsonBuilder().setPrettyPrinting().create();
        return gson;
    }
    //endregion

    //region FIREBASE
    boolean handleLogin(final Uporabnik uporabnik){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        final String TAG = "LOGIN_RESPONSE";
        final boolean[] toReturn = new boolean[1];
        final String[] userIdArr = new String[1];
        this.uporabnik = uporabnik;

        Query query = reference.child("users").orderByChild("email").equalTo(uporabnik.getEmail());

        //PREVERJANJE
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    EventBus.getDefault().post(new InfoEvent("User account status: ", "User exists"));
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        userId = issue.getKey();
                        if(issue.hasChild("accounts")) {
                            EventBus.getDefault().post(new InfoEvent("User account status: ", "Has accounts"));
                            toReturn[0] = true;
                        }

                        else
                        {
                            EventBus.getDefault().post(new InfoEvent("User account status:", "Doesn't have accounts"));
                            toReturn[0] = false;
                        }
                    }
                }

                //INSERT USER INTO DATABASE
                else{
                    EventBus.getDefault().post(new InfoEvent("User account status: ", "Creating user"));
                    reference.child("users").child(UUID.randomUUID().toString()).setValue(uporabnik);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return toReturn[0];
    }

    boolean HandleAccountCreation(String accNumber, String balance, String accType, String uId){
        final String TAG = "ACCOUNT_RESPONSE";
        Racun novRacun = new Racun(new VrstaRacuna(accType), accNumber, new BigDecimal(balance), new ArrayList<Transakcija>());
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("Users").child(uId).child("accounts").child(UUID.randomUUID().toString()).setValue(novRacun);

        return true;
    }

    String getUserID(){
        return this.userId;
    }
    //endregion

    //region WRITE TO FILE
    private void createAccountTypes(){
        seznamVrstRačunov = new SeznamVrstRacuna();
        VrstaRacuna novaVrsta = new VrstaRacuna("Osebni");
        seznamVrstRačunov.dodajRacun(novaVrsta);
        novaVrsta = new VrstaRacuna("Varčevalni");
        seznamVrstRačunov.dodajRacun(novaVrsta);
    }
    //endregion
}
