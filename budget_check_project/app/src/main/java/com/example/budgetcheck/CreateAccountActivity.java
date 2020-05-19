package com.example.budgetcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.budgetcheck.events.InfoEvent;
import com.example.datastructurelib.VrstaRacuna;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {
    public final static String TAG = "EVENT INFO";
    private MyApplicationClass mApplication;
    List<VrstaRacuna> seznamRacunov;

    Button addButton;
    TextInputEditText accountNumber;
    TextInputEditText balance;
    String dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mApplication =(MyApplicationClass) getApplication();
        this.addButton=(Button) findViewById(R.id.add_button);
        this.accountNumber = (TextInputEditText)findViewById(R.id.acc_number_text);
        this.balance = (TextInputEditText)findViewById(R.id.balance);
        this.addButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreate(accountNumber.getText().toString(), balance.getText().toString());
        }
        });
    }

    void handleCreate(String accNumber, String balance){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("ACC", accNumber);
        startActivity(intent);
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

    void getAccountTypes(){
    }
}
