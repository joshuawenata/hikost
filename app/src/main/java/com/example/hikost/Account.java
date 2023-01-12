package com.example.hikost;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.app_bar, AppBarIcon.class, null)
                    .commit();
        }
    }
}