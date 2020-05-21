package com.example.budgetcheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.budgetcheck.events.InfoEvent;
import com.example.datastructurelib.Racun;
import com.example.datastructurelib.Transakcija;
import com.example.datastructurelib.VrstaRacuna;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {
    public final static String TAG = "EVENT INFO";
    private MyApplicationClass mApplication;
    ArrayList<String>dropdownVrednosti;
    String UID;

    Button addButton;
    TextInputEditText accountNumber;
    TextInputEditText balance;
    Spinner accountTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Intent intent = getIntent();
        this.UID = intent.getStringExtra("UID");

        mApplication =(MyApplicationClass) getApplication();
        this.addButton=(Button) findViewById(R.id.add_button);
        this.accountNumber = (TextInputEditText)findViewById(R.id.acc_number_text);
        this.balance = (TextInputEditText)findViewById(R.id.balance);
        this.dropdownVrednosti=new ArrayList<>();

        accountTypeSpinner = (Spinner) findViewById(R.id.dropdown_menu);

        for (int i = 0; i<mApplication.seznamVrstRačunov.getSeznamRacunov().size(); i++){
            dropdownVrednosti.add(mApplication.seznamVrstRačunov.getSeznamRacunov().get(i).getNaziv());
        }

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,this.dropdownVrednosti);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        accountTypeSpinner.setAdapter(aa);

        this.addButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreate(accountNumber.getText().toString(), balance.getText().toString(), accountTypeSpinner.getSelectedItem().toString());
        }
        });
    }

    void handleCreate(String accNumber, String balance, String accType){
        //TODO: Ustvari račun na bazi
        //if(this.mApplication.HandleAccountCreation(accNumber, balance, accType, this.UID)){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ACC", accNumber);
        startActivity(intent);
       //}
}

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInfoEvent(InfoEvent event) {
        Log.i(TAG, "onInfoEvent"+event.toString ());
    };

}
