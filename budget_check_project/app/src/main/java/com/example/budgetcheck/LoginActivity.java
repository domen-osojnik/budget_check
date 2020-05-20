package com.example.budgetcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.budgetcheck.events.InfoEvent;
import com.example.datastructurelib.Racun;
import com.example.datastructurelib.Uporabnik;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN EVENT";
    int RC_SIGN_IN = 0;
    GoogleSignInClient mGoogleSignInClient;
    // Set the dimensions of the sign-in button.
    SignInButton signInButton;
    MyApplicationClass myAppClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myAppClass = new MyApplicationClass();

        signInButton= findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Log.d("Status", "Uspešno");

            Uporabnik prijavljenUporabnik = new Uporabnik(account.getDisplayName(),
                    account.getGivenName(), account.getFamilyName(), account.getEmail(), new ArrayList<Racun>());
            //TODO: preveri ali ima uporabnik račune, če nima, pošlji na activity za ustvarjanje računa, drugače na main activity (main menu)

            if(!myAppClass.handleLogin(prijavljenUporabnik))
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            else
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

        } catch (ApiException e) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        EventBus.getDefault().register(this);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            Uporabnik prijavljenUporabnik = new Uporabnik(account.getDisplayName(),
                    account.getGivenName(), account.getFamilyName(), account.getEmail(), new ArrayList<Racun>());
            if(!myAppClass.handleLogin(prijavljenUporabnik))
            startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            else
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInfoEvent(InfoEvent event) {
        Log.i(TAG, "onInfoEvent"+event.toString());
    };
}
