package com.example.budgetcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View contextView = findViewById(R.id.textView2);

        Intent intent = getIntent();
        this.value = intent.getStringExtra("ACC");
        Snackbar.make(contextView, "Račun  "+this.value+" ustvarjen!", Snackbar.LENGTH_LONG)
                .show();

        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivityForResult(intent, 0);
    }
}
