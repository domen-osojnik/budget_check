package com.example.budgetcheck;

import android.app.Application;
import android.util.Log;

import com.example.datastructurelib.Racun;
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

public class MyApplicationClass extends Application {
    /**
     * Spremenljivke
     */
    private DatabaseReference mDatabase;
    public boolean obstaja = false;
    public static final String TAG = MyApplicationClass.class.getName();
    public static final String MY_FILE_NAME = "DATA.json";
    static private Gson gson;
    static private File file;
    private String idAPP;
    public Uporabnik uporabnik;

    /**
     * Ob zagonu
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if(init())obstaja=true;
    }

    public void setIdApp(String newId){
        this.idAPP = newId;
    }

    public String getIdApp(){
        return this.idAPP;
    }

    /**
     * Login preverjanje
     */
    public boolean checkAccount(Uporabnik uporabnik){
        if(!obstaja){
            this.uporabnik = uporabnik;
            this.saveToFile();
            Log.d("Uporabnik:", this.uporabnik.toString());
            return false;
        }
        else{
            return true;
        }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i(TAG, file.getPath());
        return file;
    }

    public void saveToFile() {
        try {
            FileUtils.writeStringToFile(this.getFile(), getGson().toJson(this.uporabnik));
        } catch (IOException e) {
            Log.d(TAG, "Can't save "+file.getPath());
        }
    }

    private boolean readFromFile() {
        if (!getFile().exists())  return false;
        try {
            uporabnik = getGson().fromJson(FileUtils.readFileToString(getFile()), Uporabnik.class);
        } catch (IOException e) {
            Log.d("READ", "Problem in read function!");
            return false;
        }
        return true;
    }

    private boolean init() {
        if (!readFromFile()) {
            Log.d(
                    TAG,
                    "Uporabnik še nima ustvarjenega računa!"
            );
            return false;
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
        Query query = reference.child("users").orderByChild("email").equalTo(uporabnik.getEmail());
        final boolean[] toReturn = new boolean[1];

        //PREVERJANJE
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Uporabnik najden.");

                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        if(issue.child("racuni").exists()) {
                            toReturn[0] = true;
                        }
                        else  toReturn[0] = false;
                    }
                }

                //INSERT USER INTO DATABASE
                else{
                    Log.d(TAG, "Ustvarjam uporabnika.");
                    reference.child("users").child(UUID.randomUUID().toString()).setValue(uporabnik);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return toReturn[0];
    }
    //region
}
