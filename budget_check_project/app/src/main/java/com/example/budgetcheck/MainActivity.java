package com.example.budgetcheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.budgetcheck.ui.PageAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    String value;
    TabLayout tabLayout;
    ViewPager viewPager;
    TabItem mainTab;
    TabItem transactionTab;
    MyApplicationClass myApplicationClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApplicationClass = new MyApplicationClass();



        tabLayout=(TabLayout)findViewById(R.id.tabs);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        mainTab = (TabItem)findViewById(R.id.homeTab);
        transactionTab = (TabItem)findViewById(R.id.transactionTab);

        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Intent intent = getIntent();
        this.value = intent.getStringExtra("ACC");
        if(TextUtils.isEmpty(this.value)) Snackbar.make(tabLayout, "Račun že imaš.", Snackbar.LENGTH_LONG)
                .show();
        else Snackbar.make(tabLayout, "Račun  "+ this.value +" ustvarjen!", Snackbar.LENGTH_LONG)
                .show();
    }
}
