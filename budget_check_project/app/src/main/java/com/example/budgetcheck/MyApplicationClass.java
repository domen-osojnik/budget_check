package com.example.budgetcheck;

import android.app.Application;
import android.util.Log;

import com.example.datastructurelib.Uporabnik;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;




public class MyApplicationClass extends Application {
    /**
     * Spremenljivke
     */
    public boolean obstaja = false;
    public static final String TAG = MyApplicationClass.class.getName();
    public static final String MY_FILE_NAME = "DATA.json";
    static private Gson gson;
    static private File file;
    private String idAPP;
    private Uporabnik uporabnik;

    /**
     * Ob zagonu
     */
    @Override
    public void onCreate() {
        super.onCreate();
        idAPP="";
        if(init())obstaja=true;
    }

    public void setIdApp(String newId){
        this.idAPP = newId;
    }

    public String getIdApp(){
        return this.idAPP;
    }

    /**
     * GSON
     * @return
     */
    private  File getFile() {
        if (file == null) {
            File filesDir = getFilesDir();
            file = new File(filesDir, MY_FILE_NAME);
        }
        Log.i(TAG, file.getPath());
        return file;
    }

    public void saveToFile() {
        try {
            FileUtils.writeStringToFile(getFile(), getGson().toJson(uporabnik));
        } catch (IOException e) {
            Log.d(TAG, "Can't save "+file.getPath());
        }
    }

    private boolean readFromFile() {
        if (!getFile().exists())  return false;
        try {
            uporabnik = getGson().fromJson(FileUtils.readFileToString(getFile()), Uporabnik.class);
        } catch (IOException e) {
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
}
