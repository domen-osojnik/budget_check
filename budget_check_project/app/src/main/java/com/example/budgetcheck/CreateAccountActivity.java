package com.example.budgetcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.budgetcheck.events.InfoEvent;
import com.example.datastructurelib.VrstaRacuna;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {
    public final static String TAG = "EVENT INFO";
    private MyApplicationClass mApplication;
    List<VrstaRacuna> seznamRacunov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mApplication =(MyApplicationClass) getApplication();
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
        Log.i(TAG, "onInfoEvent"+event.toString());
    };

    void getAccountTypes(){
    }
}
